require.config({
	paths: {
		"jquery": "../lib/jquery-1.11.3"
	}
});

require(["jquery"], function($) {
	/* gameArea is the parentNode of all other game object */
	var gameArea;
	var point = 0;  // Game score;
	var jet = 2;    // The plane remaining
	var bomb = 2;
	var statusBarHeight;
	var playerInitX = 650;
	var playerInitY = 540;
	var bombReady = true;
	var shootReady = true;

	/* Register event function */
	$(function() {
		var start = $("#start");
		var help = $("#help");
		var exit = $("#exit");
		
		start.click(startGame);
		help.click(function() {
			showHelp();
			stopBubble(event);
		});
		exit.click(exitConfirm);
	});
	/*========================== Start the game =========================*/
	function startGame() {
		var body = $("body");
		
		var title = $("#title");
		title.css("display", "none");
		
		var game = $("<div></div>");
		game.attr("id", "game");
		var statusBar = statusBarInit();
		
		gameArea = $("<div></div>");
		gameArea.attr("id", "gamearea");
		game.append(statusBar);
		game.append(gameArea);
		body.append(game);
		
		gameInit();
	}

	/* Show help */
	function showHelp() {
		var help = $("#helpinfo");
		var menu = $("#menu");
		if (help.css("display") === "none") {
			help.css("display", "block");
			menu.css("opacity", 0.15);
		}
	}

	/* Hide help */
	$(document).click(function() {
		var help = $("#helpinfo");
		var menu = $("#menu");
		if (help.css("display") === "block") {
			help.css("display", "none");
			menu.css("opacity", 1);
		}
	});

	/** Stop bubble, ref = http://blog.csdn.net/xxd851116/article/details/4234188 */
	function stopBubble(e) {  
	    var e = e ? e : window.event;  
	    if (window.event) { // IE  
	        e.cancelBubble = true;   
	    } else { // FF  
	        e.stopPropagation();   
	    }   
	}

	/** Exit confirm */
	function exitConfirm() {
		var flag = window.confirm("Quit the game?");
		if (flag)
			window.close();
	}

	/*========================= Game interface =======================*/
	function statusBarInit() {
		var statusBar = $("<div></div>");
		statusBar.attr("id", "statusbar");
		
		var score = $("<span></span>");
		score.attr("id", "score");
		score.html("Score:&nbsp;&nbsp;" + point);
		
		var life = $("<span></span>");
		life.attr("id", "life");
		life.html("Life:&nbsp;&nbsp;" + jet);
		
		var skill = $("<span></span>");
		skill.attr("id", "bomb");
		skill.html("Bomb:&nbsp;&nbsp;" + bomb);
		
		var backToMenu = $("<span></span>");
		backToMenu.attr("id", "back");
		backToMenu.html("Abort");
		backToMenu.click(returnToMenu);
		
		statusBar.append(score);
		statusBar.append(life);
		statusBar.append(skill);
		statusBar.append(backToMenu);
		return statusBar;
	}

	function returnToMenu() {
		var flag = window.confirm("Abort the game?");
		if (flag) {
			var title = $("#title");
			var game = $("#game");
			var body = $("body")[0];
			game.remove();
			delete game;
			title.css("display", "block");
			clearInterval(detectId);
		}
		return flag;
	}

	/*========================= Game Initialize =====================*/
	var detectId;
	var actionTime = 200;
	var shootTime = 2000;
	var flyTime = 15000;
	var genTime = 2000;
	var range = 2;
	/****************************Game Initialize**************************/
	function gameInit() {
		point = 0;  // Game score, last time set
		jet = 2;
		bomb = 2;
		statusBarHeight = $("#statusbar").height();
		genPlayer(playerInitX, playerInitY);

		var counter = 0;	
		detectId = setInterval(function() {
			detectCollision();
			counter++;
			if ((counter * actionTime) % genTime === 0) {
				var pattern = Math.round(Math.random() * range);
				enemyFactory(pattern);
			}
			if (counter === 50) {
				var fact = point / 5000;
				if (fact > 1) {
					if (shootTime > 1000)
						shootTime = 3000 - 500 * Math.floor(fact);
					if (genTime > 1000)
						genTime = 2000 - 200 * Math.floor(fact);
					if (range < 6)
						range = 2 + fact;
				}
				counter = 0;
			}
		}, actionTime);
	}
	/*********************************************************************/
	/*-------------- Global variables for controling players -------------*/
	var upKeyDown = false;
	var downKeyDown = false;
	var leftKeyDown = false;
	var rightKeyDown = false;
	var shiftKeyDown = false;
	var shootKeyDown = false;
	var shootInterval;
	var motionTimeId;
	var defaultV = 3;

	/*-------------------- Generate players ------------------*/
	function genPlayer(/*coordinate*/x, y) {
		var plane = playerPlane(x, y);
		var player = Plane(plane, defaultV);
		shootInterval = 0;
		shootReady = true;

		// Define control keys
		$(document).keydown(function() {
			setKey(true);
			if (event.keyCode === 88 && bomb > 0 && bombReady) { // x skill
				bombReady = false;
				bomb--;
				update("bomb", bomb);
				shootAll();
				setTimeout(function() {bombReady = true;}, 1000);
			}
		});
		
		$(document).keyup(function() {
			setKey(false);
		});
		
		motionTimeId = setInterval(function() {
			setMotion(player);
			if (shootInterval === 10) {
				shootInterval = 0;
			} else {
				shootInterval++;
			}
		}, 10);
	}

	function setKey(value) {
		if (event.keyCode === 37) {// Left arrow
			leftKeyDown = value;
		} else if (event.keyCode === 38) {// Up arrow
			upKeyDown = value;
		} else if (event.keyCode === 39) {// Right arrow
			rightKeyDown = value;
		} else if (event.keyCode === 40) {// Down arrow
			downKeyDown = value;
		} else if (event.keyCode === 16) {// Shift
			shiftKeyDown = value;
		}  else if (event.keyCode === 90) {// z
			shootKeyDown = value;
		}
	}

	function setMotion(player) {
		if (upKeyDown) {
			playerUp(player);
		}
		if (downKeyDown) {
			playerDown(player);
		}
		if (leftKeyDown) {
			playerLeft(player);
		}
		if (rightKeyDown) {
			playerRight(player);
		}
		if (shiftKeyDown) {
			player.v = 1;
		}
		if (!shiftKeyDown) {
			player.v = defaultV;
		}
		if (shootKeyDown) {
			if (shootInterval === 0) {
				player.fire(player.offset().left + player.width() / 2, 0, 2);
			}
		}
	}
	/* Functions to control player's movement */
	//--------------------------------------------------------------------
	function playerUp(player) {
		var newPos = player.offset().top - player.v;
		if (newPos >= statusBarHeight) {
			player.moveUp();
		}
	}

	function playerDown(player) {
		var newPos = player.offset().top + player.v + player.height();
		if (newPos <= window.screen.availHeight - 90) {
			player.moveDown();	
		}
	}

	function playerLeft(player) {
		var newPos = player.offset().left - player.v;
		if (newPos >= 0) {
			player.moveLeft();	
		}
	}

	function playerRight(player) {
		var newPos = player.offset().left + player.v + player.width();
		if (newPos <= window.screen.availWidth - 5) {
			player.moveRight();	
		}
	}
	//--------------------------------------------------------------------
	/*--------------------- Generate enemies ----------------------------*/
	function enemyFactory(pattern) {
		var pos;
		switch(pattern) {
			case 0: pos = Math.random() * 300 + 100;
					genEnemy(0, pos, 1380, 200, flyTime, shootTime);
					break;
			case 1: pos = Math.random() * 300 + 100;
					genEnemy(1350, pos, -10, 200, flyTime, shootTime);
					break;
			case 2: pos = Math.random() * 1000;
					genEnemy(pos, 0, pos -100, 700, flyTime, shootTime);
					break;
			case 3: pos = Math.random() * 1000;
					genEnemy(pos, 0, pos + 100, 700, flyTime, shootTime);
					break;
			case 4: pos = Math.random() * 300 + 200;
					genEnemy(0, pos, 1380, 100, flyTime, shootTime);
					break;
			case 5: pos = Math.random() * 300 + 200;
					genEnemy(1350, pos, -10, 200, flyTime, shootTime);
					break;
			default:pos = Math.random() * 1000;
					genEnemy(pos, 0, 1380, 75, flyTime, shootTime);
					break;
		}
	}

	function genEnemy(/*coordinate*/x, y, /*destination*/sx, sy, time, shootTime) {
		var plane = enemyPlane(x, y);
		var enemy = Plane(plane, 10);
		enemy.timeId = enemyAction(enemy, sx, sy, time, shootTime);
		return enemy;
	}

	function enemyAction(enemy, sx, sy, time, shootTime) {
		var timeInterval = time;
		var count = 0;

		var timeid = setInterval(function() {
			var player = $(".player");
			if (player !== null && player !== undefined) {
				var speedX = player.offset().left - enemy.offset().left;
				var speedY = player .offset().top - enemy.offset().top;

				var factX = 1500 / Math.abs(speedX);
				var factY = 800 / Math.abs(speedY);
				var fact = factX > factY ? factX : factY;

				var destX = speedX * fact;
				var destY = speedY * fact;
				enemy.fire( destX, destY, 2);
			}
		}, shootTime);

		enemy.animate({
			left: sx,
			top: sy
		}, time, "linear", function() {
			clearInterval(timeid);
			enemy.remove();
			delete enemy;	
		});
		return timeid;
	}

	/*----------------- Init methods-------------------- */
	function genSquare(/*size*/w, h) {
		var square = $("<div></div>");
		square.css("width", w + "px");
		square.css("height", h + "px");
		square.css("position", "absolute");
		return square;
	}

	/** ---------------------Player plane -------------------------*/
	function playerPlane(/*coordinate*/x, y) {
		var plane = genSquare(16, 16);
		plane.css("border", "2px solid black");
		plane.attr("class", "player");
		plane.css("top", y +"px");
		plane.css("left", x + "px");
		gameArea.append(plane);
		return plane;
	}

	/**-------------------------- Enemy plane -------------------------*/
	function enemyPlane(/*coordinate*/x, y) {
		var plane = genSquare(20, 20);
		plane.css("backgroundColor", "black");
		plane.attr("class", "enemy");
		plane.css("top", y +"px");
		plane.css("left", x + "px");
		gameArea.append(plane);
		return plane;
	}

	function Plane(pbody, velocity) {
		pbody.v = velocity;
		pbody.timeId = null;
		pbody.moveUp = function() {
			var newPos = pbody.offset().top - this.v;
			pbody.offset({top: newPos});
		};
		
		pbody.moveDown = function() {
			var newPos = pbody.offset().top + this.v;
			pbody.offset({top: newPos});
		};
		
		pbody.moveRight = function() {
			var newPos = pbody.offset().left + this.v;
			pbody.offset({left: newPos});
		};
		
		pbody.moveLeft = function() {
			var newPos = pbody.offset().left - this.v;
			pbody.offset({left: newPos});
		};
		
		pbody.fire = function(/* bullet dest */x, y, speed) {
			var startX = pbody.offset().left + pbody.width() / 2 - 2;
			var startY = pbody.offset().top + pbody.height() / 2 - 2.5 - statusBarHeight;
			if (startX < 0 || startY < 0) {
				return;
			}
			var bullet = bulletBody(startX, startY, pbody.attr("class") + "bullet");

			var sx = x - startX;
			var sy = y - startY;
			var time = Math.sqrt(Math.pow(sx, 2) + Math.pow(sy, 2)) / speed * 10;
			bullet.shoot(x, y, time);			
		};
		return pbody;
	}

	function destroyPlane(obj) {
		obj.css("border", "red");
		obj.css("background", "red");
		setTimeout(function() {
			obj.remove();
			delete obj;
		}, actionTime);
	}

	/**------------------- Bullet body ---------------------*/
	function bulletBody(/*coordinate*/x, y, name) {
		var bullet = genSquare(4, 5);
		bullet.attr("class", name);
		if (name === "enemybullet") {
			bullet.css("background-color", "black");
		}
		if (name === "playerbullet") {
			bullet.css("border", "1px solid black");
		}
		bullet.offset({top: y});
		bullet.offset({left: x});
		gameArea.append(bullet);
		bullet.shoot = function(destX, destY, time) {
			fly(bullet, destX, destY, time);
		};
		return bullet;
	}

	/*=========================== other utility ==================================*/
	/* This function does not move an object to a destination, the finalX and finalY parameters are used to calculate velocity */
	function fly(obj, /* Coordinates used to calculate velocity */destX, destY, time) {
		var timeInterval = time;

		obj.animate({
			left: destX,
			top: destY
		}, time, "linear", function() {
			obj.remove();
			delete obj;
		});
	}

	/** collision detection */
	function collide(a, b) {
		if (a === null || a === undefined || b === null || b === undefined) {
			return false;
		} else {
			var xa = a.offset().left + a.width() / 2;
			var ya = a.offset().top + a.height() / 2;
			var xb = b.offset().left + b.width() / 2;
			var yb = b.offset().top + b.height() / 2;
			var dist = Math.sqrt(Math.pow(xa - xb, 2) + Math.pow(ya - yb, 2));
			if (dist <= 20) {
				return true;
			} else {
				return false;
			}
		}
	}

	/** get all target object and detect collision */
	function detectCollision() {
		var enemy = $(".enemy");
		var enemyBullet = $(".enemybullet");
		var playerBullet = $(".playerbullet");

		// Enemies and player
		for (var i = 0; i < enemy.length; i++) {
			var cflag = collide($(".player"), $(enemy[i]));
			if (cflag) {
				destroyPlane($(".player"));
				destroyPlane($(enemy[i]));
				jet--;
				point += 100;
				update("score", point);
				if (jet < 0) {
					gameOver();
					return;
				}
				bonus();
				update("life", jet);
				genPlayer(playerInitX, playerInitY);
			}
		}
		
		// Enemybullets and player
		for (var i = 0; i < enemyBullet.length; i++) {
			var cflag = collide($(".player"), $(enemyBullet[i]));
			if (cflag) {
				destroyPlane($(".player"));
				$(enemyBullet[i]).remove();
				delete enemyBullet[i];
				jet--;
				if (jet < 0) {
					gameOver();
					return;
				}
				update("life", jet);
				genPlayer(playerInitX, playerInitY);
			}
		}
		// Playerbullets and enemies
		for (var i = 0; i < enemy.length; i++) {
			for (var j = 0; j < playerBullet.length; j++) {
				var cflag = collide($(enemy[i]), $(playerBullet[j]));
				if (cflag) {
					destroyPlane($(enemy[i]));
					playerBullet[j].remove();
					delete playerBullet[j];
					point += 100;
					update("score", point);
					bonus();
					break;
				}
			}
		}
	}

	function shootAll() { // x skill
		var enemy = $(".enemy");
		point += 100 * enemy.length;
		update("score", point);
		destroyPlane(enemy);
		$(".enemybullet").remove();
		delete $(".enemybullet");
		bonus();
	}

	function bonus() {
		if (point % 3000 === 0 && point > 0) {
			bomb++;
			update("bomb", bomb);
		}
		
		if (point % 5000 === 0 && point > 0) {
			jet++;
			update("life", jet);
		}
	}

	/* Update values */
	function update(id, value) {
		var node = $("#" + id);
		if (id === "score") {
			node.html("Score:&nbsp;&nbsp;" + value);
		} else if (id === "life") {
			node.html("Life:&nbsp;&nbsp;" + value);
		} else if (id === "bomb") {
			node.html("Bomb:&nbsp;&nbsp;" + value);
		}
	}

	function gameOver() {
		clearInterval(detectId);
		clearInterval(motionTimeId);
		var sbar = $("#statusbar");
		var garea = $("#gamearea");
		sbar.css("opacity", 0.15);
		garea.css("opacity", 0.15);
		
		var bodyNode = $("body");
		var result = $("<div></div>");
		result.attr("id", "gameover");
		result.html("Game Over <br /> Your Score is:" + point + "<br />Press 'Spacebar' to restart or 'Enter' to quit");
		result.css("display", "block");
		bodyNode.append(result);
		
		$(document).keydown(function() {
			if (event.keyCode === 32) {// space bar to restart;
				result.css("display", "none");
				sbar.css("opacity", 1);
				garea.css("opacity", 1);
				gameInit();
				update("life", jet);
				update("score", point);	
				update("bomb", 2);	
			}
			if (event.keyCode === 13) { // enter to quit
				result.css("display", "none");
				if(!returnToMenu())
					result.css("display", "block");
			}
		});
	}
});	