/* gameArea is the parentNode of all other game object */
var gameArea;
var point = 0;  // Game score;
var jet = 2;    // The plane remaining
var bomb = 2;

/* Register event function */
window.onload = function() {
	var start = doc.byId("start");
	var help = doc.byId("help");
	var exit = doc.byId("exit");
	
	start.onclick = startGame;
	help.onclick = function() {
		showHelp();
		stopBubble(event);
	}
	exit.onclick = exitConfirm;
}
/*========================== Start the game =========================*/
function startGame() {
	var body = doc.byTag("body")[0];
	
	var title = doc.byId("title");
	title.style.display = "none";
	
	var game = doc.createElement("div");
	game.id = "game";
	var statusBar = statusBarInit();
	
	gameArea = doc.createElement("div");
	gameArea.id = "gamearea";
	game.appendChild(statusBar);
	game.appendChild(gameArea);
	body.appendChild(game);
	
	gameInit();
}

/* Show help */
function showHelp() {
	var help = doc.byId("helpinfo");
	var menu = doc.byId("menu");
	if (window.getComputedStyle(help, null).display == "none") {
		help.style.display = "block";
		menu.style.opacity = 0.15;
	}
}

/* Hide help */
document.onclick = function() {
	var help = doc.byId("helpinfo");
	var menu = doc.byId("menu");
	if (window.getComputedStyle(help, null).display == "block") {
		help.style.display = "none";
		menu.style.opacity = 1;
	}
}

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
	var statusBar = doc.createElement("div");
	statusBar.id = "statusbar";
	
	var score = doc.createElement("span");
	score.id = "score";
	score.innerHTML = "Score:&nbsp;&nbsp;" + point;
	
	var life = doc.createElement("span");
	life.id = "life";
	life.innerHTML = "Life:&nbsp;&nbsp;" + jet;
	
	var skill = doc.createElement("span");
	skill.id = "bomb";
	skill.innerHTML = "Bomb:&nbsp;&nbsp;" + bomb;
	
	var backToMenu = doc.createElement("span");
	backToMenu.id = "back";
	backToMenu.innerHTML = "Abort";
	backToMenu.onclick = returnToMenu;
	
	statusBar.appendChild(score);
	statusBar.appendChild(life);
	statusBar.appendChild(skill);
	statusBar.appendChild(backToMenu);
	return statusBar;
}

function returnToMenu() {
	var flag = window.confirm("Abort the game?");
	if (flag) {
		var title = doc.byId("title");
		var game = doc.byId("game");
		var body = doc.byTag("body")[0];
		body.removeChild(game);
		title.style.display = "block";
		clearInterval(detectId);
	}
	return flag;
}

/*========================= Game Initialize =====================*/
var planePlayer; // Used for enemy target
var detectId;

var shootTime = 2000;
var flyTime = 100;
var genTime = 200;
var range = 2;
/****************************Game Initialize**************************/
function gameInit() {
	point = 0;  // Game score, last time set
	jet = 2;
	bomb = 2;
	var player = genPlayer(650, 550);
	planePlayer = player.planeBody;

	var counter = 0;
	detectId = setInterval(function() {
		detectCollision();
		counter++;
		if (counter % genTime == 0) {
			var pattern = Math.round(Math.random() * range);
			enemyFactory(pattern);
		}
		if (counter == 1000) {
			var fact = point / 5000;
			if (fact > 1) {
				if (shootTime > 1000)
					shootTime = 3000 - 500 * Math.floor(fact);
				if (genTime > 100)
					genTime = 200 - 20 * Math.floor(fact);
				if (range < 6)
					range = 2 + fact;
			}
			counter = 0;
		}
	}, 10);
}
/*********************************************************************/
/*-------------- Global variables for controling players -------------*/
var upKeyDown = false;
var downKeyDown =false;
var leftKeyDown = false;
var rightKeyDown = false;
var shiftKeyDown = false;

var motionTimeId;
var defaultV = 3;
var shootInterval = 0;

/*-------------------- Generate players ------------------*/
function genPlayer(/*coordinate*/x, y) {
	var plane = playerPlane(x, y);
	var player = new Plane(plane, defaultV);
	var shootInterval = 0;
	// Define control keys
	document.onkeydown = function() {
		setKey(true);
		if (event.keyCode == 90) {// z to fire
			if (shootInterval == 0)
				player.fire(player.planeBody.offsetLeft + player.planeBody.offsetWidth / 2, 0, 10, 5);
			shootInterval++;
		}
		if (event.keyCode == 88 && bomb > 0) { // x skill
			bomb--;
			update("bomb", bomb);
			shootAll();
		}
	}
	
	document.onkeyup = function() {
		setKey(false);
	}
	
	var counter = 0;
	motionTimeId = setInterval(function() {
		setMotion(player);
		counter++;
		if (counter == 10) {
			shootInterval = 0;
			counter = 0;
		}
	}, 10);

	return player;
}

function setKey(value) {
	if (event.keyCode == 37)// Left arrow
		leftKeyDown = value;
	if (event.keyCode == 38)// Up arrow
		upKeyDown = value;
	if (event.keyCode == 39)// Right arrow
		rightKeyDown = value;
	if (event.keyCode == 40)// Down arrow
		downKeyDown = value;
	if (event.keyCode == 16)// Shift
		shiftKeyDown = value;
}

function setMotion(player) {
	if (upKeyDown)
		playerUp(player);
	if (downKeyDown)
		playerDown(player);
	if (leftKeyDown)
		playerLeft(player);
	if (rightKeyDown)
		playerRight(player);
	if (shiftKeyDown)
		player.v = 1;
	if (!shiftKeyDown)
		player.v = defaultV;
}
/* Functions to control player's movement */
//--------------------------------------------------------------------
function playerUp(player) {
	var newPos = player.planeBody.offsetTop - player.v;
	if (newPos >= 0)
		player.moveUp();
}

function playerDown(player) {
	var newPos = player.planeBody.offsetTop + player.v + player.planeBody.offsetHeight;
	if (newPos <= window.screen.availHeight - 135)
		player.moveDown();	
}

function playerLeft(player) {
	var newPos = player.planeBody.offsetLeft - player.v;
	if (newPos >= 0)
		player.moveLeft();	
}

function playerRight(player) {
	var newPos = player.planeBody.offsetLeft + player.v + player.planeBody.offsetWidth;
	if (newPos <= window.screen.availWidth)
		player.moveRight();	
}
//--------------------------------------------------------------------
/*--------------------- Generate enemies ----------------------------*/
function enemyFactory(pattern) {
	var pos;
	switch(pattern) {
		case 0: pos = Math.random() * 300;
				genEnemy(0, pos, 5, 1, flyTime, shootTime);
				break;
		case 1: pos = Math.random() * 300;
				genEnemy(1360, pos, -5, 1, flyTime, shootTime);
				break;
		case 2: pos = Math.random() * 1000;
				genEnemy(pos, 0, 0, 5, flyTime, shootTime);
				break;
		case 3: pos = Math.random() * 1000;
				genEnemy(pos, 0, 0, 10, flyTime, shootTime);
				break;
		case 4: pos = Math.random() * 300 + 200;
				genEnemy(0, pos, 5, -1, flyTime, shootTime);
				break;
		case 5: pos = Math.random() * 300 + 200;
				genEnemy(1360, pos, -5, -1, flyTime, shootTime);
				break;
		default:pos = Math.random() * 1000;
				genEnemy(pos, 0, 5, 1, flyTime, shootTime);
				break;
	}
}

function genEnemy(/*coordinate*/x, y, /*flying speed*/sx, sy, time, shootTime) {
	var plane = enemyPlane(x, y);
	var enemy = new Plane(plane, 10);
	enemyAction(enemy, sx, sy, time, shootTime);
	return enemy;
}

function enemyAction(enemy, sx, sy, time, shootTime) {
	var obj = enemy.planeBody;
	var timeInterval = time;
	var count = 0;
	
	var timeid = setInterval(function() {
		var newX = obj.offsetLeft + sx;
		var newY = obj.offsetTop + sy;
		obj.style.left = newX + "px";
		obj.style.top = newY + "px";
		if(detectOutBorder(obj)) {
			clearInterval(timeid);
			gameArea.removeChild(obj);
		}
		count++;
		if (count >= shootTime / time) {
			if (doc.byClass("player")) {
				enemy.fire(planePlayer.offsetLeft, planePlayer.offsetTop, 1, 2);
				count = 0;
			}
		}
	}, timeInterval);
}

/*----------------- Init methods-------------------- */
function genSquare(/*size*/w, h) {
	var square = doc.createElement("div");
	square.style.width = w + "px";
	square.style.height = h + "px";
	square.style.position = "absolute";
	return square;
}

/** ---------------------Player plane -------------------------*/
function playerPlane(/*coordinate*/x, y) {
	var plane = genSquare(16, 16);
	plane.style.border = "2px solid black";
	plane.className = "player";
	plane.style.top = y +"px";
	plane.style.left = x + "px";
	gameArea.appendChild(plane);
	return plane;
}

/**-------------------------- Enemy plane -------------------------*/
function enemyPlane(/*coordinate*/x, y) {
	var plane = genSquare(20, 20);
	plane.style.backgroundColor = "black";
	plane.className = "enemy";
	plane.style.top = y +"px";
	plane.style.left = x + "px";
	gameArea.appendChild(plane);
	return plane;
}

/**----------------- Plane Object --------------------------------*/
function Plane(pbody, velocity) {
	this.planeBody = pbody;
	this.v = velocity;
	this.moveUp = function() {
		var newPos = this.planeBody.offsetTop - this.v;
		this.planeBody.style.top = newPos + "px";
	}
	
	this.moveDown = function() {
		var newPos = this.planeBody.offsetTop + this.v;
		this.planeBody.style.top = newPos + "px";
	}
	
	this.moveRight = function() {
		var newPos = this.planeBody.offsetLeft + this.v;
		this.planeBody.style.left = newPos + "px";
	}
	
	this.moveLeft = function() {
		var newPos = this.planeBody.offsetLeft - this.v;
		this.planeBody.style.left = newPos + "px";
	}
	
	this.fire = function(/* bullet dest */x, y, time, speed) {
		var startX = this.planeBody.offsetLeft + this.planeBody.offsetWidth / 2 - 2;
		var startY = this.planeBody.offsetTop + this.planeBody.offsetHeight / 2 - 2.5;
		
		var bBody = bulletBody(startX, startY, this.planeBody.className + "bullet");
		var bullet = new Bullet(bBody);
		
		var sx = x - startX;
		var sy = y - startY;
		var fact = Math.sqrt(Math.pow(sx, 2) + Math.pow(sy, 2));
		
		bullet.shoot(sx / fact * speed, sy / fact * speed, time);
	}
}

	
function destroyPlane(obj) {
	obj.style.border = "red";
	obj.style.background = "red";
	setTimeout(function() {gameArea.removeChild(obj)}, 200);
}

/**------------------- Bullet body ---------------------*/
function bulletBody(/*coordinate*/x, y, name) {
	var bullet = genSquare(4, 5);
	bullet.className = name;
	if (name == "enemybullet")
		bullet.style.backgroundColor = "black";
	if (name == "playerbullet")
		bullet.style.border = "1px solid black";
	bullet.style.top = y +"px";
	bullet.style.left = x + "px";
	gameArea.appendChild(bullet);
	return bullet;
}

/**-------------------- Bullet Object --------------------*/
function Bullet(bullet) {
	this.bulletBody = bullet;
	this.shoot = function(speedX, speedY, time) {
		fly(this.bulletBody, speedX, speedY, time);
	}
}

/*=========================== other utility ==================================*/
/* This function does not move an object to a destination, the finalX and finalY parameters are used to calculate velocity */
function fly(obj, /* Coordinates used to calculate velocity */stepX, stepY, time) {
	var timeInterval = time;

	var timeid = setInterval(function() {
		var newX = obj.offsetLeft + stepX;
		var newY = obj.offsetTop + stepY;
		obj.style.left = newX + "px";
		obj.style.top = newY + "px";
		if(detectOutBorder(obj)) {
			clearInterval(timeid);
			gameArea.removeChild(obj);
		}
	}, timeInterval);
}

/** collision detection */
function collide(a, b) {
	var xa = a.offsetLeft + a.offsetWidth / 2;
	var ya = a.offsetTop + a.offsetHeight / 2;
	var xb = b.offsetLeft + b.offsetWidth / 2;
	var yb = b.offsetTop + b.offsetHeight / 2;
	var dist = Math.sqrt(Math.pow(xa - xb, 2) + Math.pow(ya - yb, 2));
	if (dist <= 15)
		return true;
	else
		return false;
}

/** Detect whether an object is out of the border*/
function detectOutBorder(obj) {
	var objTop = obj.offsetTop + obj.offsetHeight;
	var objBottom = obj.offsetTop;
	var objLeft = obj.offsetLeft + obj.offsetWidth;
	var objRight = obj.offsetLeft;

	var top = 1;
	var bottom = window.screen.availHeight;
	var left = 1;
	var right = window.screen.availWidth;

	if (objTop <= top || objBottom >= bottom || objLeft <= left || objRight >= right)
		return true;
	return false;
}

/** get all target object and detect collision */
function detectCollision() {
	var enemy = doc.byClass("enemy");
	var enemyBullet = doc.byClass("enemybullet");
	var playerBullet = doc.byClass("playerbullet");

	// Enemies and player
	for (var i = 0; i < enemy.length; i++) {
		var cflag = collide(planePlayer, enemy[i]);
		if (cflag) {
			destroyPlane(planePlayer);
			destroyPlane(enemy[i]);
			jet--;
			point += 100;
			update("score", point);
			if (jet < 0) {
				gameOver();
				return;
			}
			bonus();
			update("life", jet);
			var player = genPlayer(650, 550);
			planePlayer = player.planeBody;
		}
	}
	
	// Enemybullets and player
	for (var i = 0; i < enemyBullet.length; i++) {
		var cflag = collide(planePlayer, enemyBullet[i]);
		if (cflag) {
			destroyPlane(planePlayer);
			gameArea.removeChild(enemyBullet[i]);
			jet--;
			if (jet < 0) {
				gameOver();
				return;
			}
			update("life", jet);
			var player = genPlayer(650, 550);
			planePlayer = player.planeBody;
		}
	}
	// Playerbullets and enemies
	for (var i = 0; i < enemy.length; i++) {
		for (var j = 0; j < playerBullet.length; j++) {
			var cflag = collide(enemy[i], playerBullet[j]);
			if (cflag) {
				destroyPlane(enemy[i]);
				gameArea.removeChild(playerBullet[j]);
				point += 100;
				update("score", point);
				bonus();
				break;
			}
		}
	}
}

function shootAll() { // x skill
	var enemy = doc.byClass("enemy");
	for (var i = 0; i < enemy.length; i++) {
		destroyPlane(enemy[i]);
		point += 100;
		update("score", point);
		bonus();
	}
}

function bonus() {
	if (point % 3000 == 0) {
		bomb++;
		update("bomb", bomb);
	}
	
	if (point % 5000 == 0) {
		jet++;
		update("life", jet);
	}
}

/* Update values */
function update(id, value) {
	var node = doc.byId(id);
	if (id == "score")
		node.innerHTML = "Score:&nbsp;&nbsp;" + value;
	if (id == "life")
		node.innerHTML = "Life:&nbsp;&nbsp;" + value;
	if (id == "bomb")
		node.innerHTML = "Bomb:&nbsp;&nbsp;" + value;
}

function gameOver() {
	clearInterval(detectId);
	clearInterval(motionTimeId);
	var sbar = doc.byId("statusbar");
	var garea = doc.byId("gamearea");
	sbar.style.opacity = 0.15;
	garea.style.opacity = 0.15;
	
	var bodyNode = doc.byTag("body")[0];
	var result = doc.createElement("div");
	result.id = "gameover";
	result.innerHTML = "Game Over <br /> Your Score is:" + point + "<br />Press 'Spacebar' to restart or 'Enter' to quit";
	result.style.display = "block";
	bodyNode.appendChild(result);
	
	document.onkeydown = function() {
		if (event.keyCode == 32) {// space bar to restart;
			result.style.display = "none";
			sbar.style.opacity = 1;
			garea.style.opacity = 1;
			gameInit();
			update("life", jet);
			update("score", point);			
		}
		if (event.keyCode == 13) { // enter to quit
			result.style.display = "none";
			if(!returnToMenu())
				result.style.display = "block";
		}
	}
}