define("shoot_plane", ["util"], function( util ) {

// General game data
var gameArea;
var point = 0;  // Game score;
var jet = 2;    // The plane remaining
var bomb = 2;

// Control variables
var upKeyDown = false;
var downKeyDown =false;
var leftKeyDown = false;
var rightKeyDown = false;
var shiftKeyDown = false;

// Enemy generation control
var detectId;

var shootTime = 2000;
var flyTime = 100;
var genTime = 200;
var range = 2;

// Player control
var motionTimeId;
var defaultV = 3;
var enemyV = 10;
var shootInterval = 0;

/* Update values */
var update = function(id, value) {
    var score = "Score:&nbsp;&nbsp;";
    var life = "Life:&nbsp;&nbsp;";
    var skill = "Bomb:&nbsp;&nbsp;";

    if (arguments.length === 0) {
        doc.byId("score").innerHTML = [score, point].join("");
        doc.byId("life").innerHTML = [life, jet].join("");
        doc.byId("bomb").innerHTML = [skill, bomb].join("");
    }

    var node = doc.byId(id);
    if (id === "score")
        node.innerHTML = [score, value].join("");
    if (id === "life")
        node.innerHTML = [life, value].join("");
    if (id === "bomb")
        node.innerHTML = [skill, value].join("");
};

var keyRegister = function(fire, shootAll) {
    var setKey = function(value) {
        if (event.keyCode === 37)// Left arrow
            leftKeyDown = value;
        if (event.keyCode === 38)// Up arrow
            upKeyDown = value;
        if (event.keyCode === 39)// Right arrow
            rightKeyDown = value;
        if (event.keyCode === 40)// Down arrow
            downKeyDown = value;
        if (event.keyCode === 16)// Shift
            shiftKeyDown = value;
    };

    document.onkeydown = function() {
        setKey(true);
        if (event.keyCode === 90) {// z to fire
            if (fire) fire();// Input function used here
        }
        if (event.keyCode === 88 && bomb > 0) { // x skill
            bomb--;
            update("bomb", bomb);
            if (shootAll) shootAll();// Input function used here
        }
    };
    
    document.onkeyup = function() {
        setKey(false);
    };
};

var moveActionReg = function(action) {
    if (upKeyDown) action.onUp();
    if (downKeyDown) action.onDown();
    if (leftKeyDown) action.onLeft();
    if (rightKeyDown) action.onRight();
    if (shiftKeyDown) action.onSlow();
    if (!shiftKeyDown) action.onNormal();
};

var bonus = function() {
    if (point % 3000 === 0) {
        bomb++;
        update("bomb", bomb);
    }
    
    if (point % 5000 === 0) {
        jet++;
        update("life", jet);
    }
};

// * get all target object and detect collision 
// param is an object:
// -----------------
// player:        obj
// enemy:         array
// enemyBullet:   array
// playerBullet:  array
// detectCollide:   function
// 
// gameOver:        function
// gameRestart:     function
var detectCollision = function(param) {
    var i, j, cflag;
    // Enemies and player
    for (i = 0; i < param.enemy.length; i++) {
        cflag = param.detectCollide(param.player, param.enemy[i]);
        if (cflag) {
            cflag = false;
            param.player.destroy();
            param.enemy[i].destroy();
            param.enemy.splice(i, 1);
            jet--;
            point += 100;
            update("score", point);
            if (jet < 0) {
                param.gameOver();
                return;
            }
            bonus();
            update("life", jet);
            param.gameRestart();
        }

        // Playerbullets and enemies
        for (j = 0; j < param.playerBullet.length; j++) {
            cflag = param.detectCollide(param.playerBullet[j], param.enemy[i]);
            if (cflag) {
                cflag = false;
                param.enemy[i].destroy();
                param.playerBullet[j].destroy();
                param.enemy.splice(i, 1);
                param.playerBullet.splice(j, 1);
                point += 100;
                update("score", point);
                bonus();
                break;
            }
        }
    }
    
    // Enemybullets and player
    for (i = 0; i < param.enemyBullet.length; i++) {
        cflag = param.detectCollide(param.enemyBullet[i], param.player);
        if (cflag) {
            cflag = false;
            param.player.destroy();
            param.enemyBullet[i].destroy();
            param.enemyBullet.splice(i, 1);
            jet--;
            if (jet < 0) {
                param.gameOver();
                return;
            }
            update("life", jet);
            param.gameRestart();
        }
    }
};

// ***************************Game Initialize*************************
var gameInit = function(detectCollision, enemyFactory) {
    point = 0;  // Game score, last time set
    jet = 2;
    bomb = 2;
    update();

    var counter = 0;
    detectId = setInterval(function() {
        if (detectCollision) detectCollision();
        counter++;
        if (counter % genTime === 0) {
            var pattern = Math.round(Math.random() * range);
            if (enemyFactory) enemyFactory(pattern);
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
};

var gameOver = function(initGame) {
    clearInterval(detectId);
    clearInterval(motionTimeId);
    var sbar = doc.byId("statusbar");
    var garea = doc.byId("game");
    sbar.style.opacity = 0.15;
    garea.style.opacity = 0.15;
    
    var resultNode = doc.byId("gameover");
    doc.byId("point").textContent = point;
    resultNode.style.display = "block";
    
    document.onkeydown = function() {
        if (event.keyCode === 32) {// space bar to restart;
            resultNode.style.display = "none";
            sbar.style.opacity = 1;
            garea.style.opacity = 1;
            initGame();
            update("life", jet);
            update("score", point);         
        }
        if (event.keyCode === 13) { // enter to quit
            resultNode.style.display = "none";
            if(!confirm("Quit game?"))
                resultNode.style.display = "block";
        }
    };
};

var domGame = (function() {

var planePlayer; // Used for enemy target
var enemy = [], enemyBullet = [], playerBullet = [];

// * Detect whether an object is out of the border
var detectOutBorder = function(obj) {
    var objTop = obj.offsetTop + obj.offsetHeight;
    var objBottom = obj.offsetTop;
    var objLeft = obj.offsetLeft + obj.offsetWidth;
    var objRight = obj.offsetLeft;

    var top = 1;
    var bottom = window.screen.availHeight;
    var left = 1;
    var right = window.screen.availWidth;

    return (objTop <= top || objBottom >= bottom || objLeft <= left || objRight >= right);
};

// ----------------- Init methods-------------------- 
var genSquare = function(/*size*/w, h, /*position*/x, y) {
    var square = doc.createElement("div");
    square.style.width = w + "px";
    square.style.height = h + "px";
    square.style.position = "absolute";
    square.style.top = y +"px";
    square.style.left = x + "px";
    gameArea.appendChild(square);
    return square;
};

/**-------------------- Bullet Object --------------------*/
var Bullet = function(/*coordinate*/x, y, name) {
    var bullet = genSquare(4, 5, x, y);
    bullet.className = name;
    if (name === "enemybullet")
        bullet.style.backgroundColor = "black";
    if (name === "playerbullet")
        bullet.style.border = "1px solid black";

    this.body = bullet;
};

Bullet.prototype = {
    body: undefined,
    shoot: function(speedX, speedY, time) {
        var obj = this.body;

        var timeid = setInterval(function() {
            var newX = obj.offsetLeft + speedX;
            var newY = obj.offsetTop + speedY;
            obj.style.left = newX + "px";
            obj.style.top = newY + "px";
            if(detectOutBorder(obj)) {
                clearInterval(timeid);
                if (obj.parentNode) gameArea.removeChild(obj);
                // delete this;
            }
        }, time);
    },
    destroy: function() {
        if (this.body.parentNode) gameArea.removeChild(this.body);
        // delete this;
    }
};

var planeProto = {
    body: undefined,
    xv: undefined,
    yv: undefined,
    moveUp: function() {
        var newPos = this.body.offsetTop - this.yv;
        this.body.style.top = newPos + "px";
    },
    
    moveDown: function() {
        var newPos = this.body.offsetTop + this.yv;
        this.body.style.top = newPos + "px";
    },
    
    moveRight: function() {
        var newPos = this.body.offsetLeft + this.xv;
        this.body.style.left = newPos + "px";
    },
    
    moveLeft: function() {
        var newPos = this.body.offsetLeft - this.xv;
        this.body.style.left = newPos + "px";
    },
    
    fire: function(/* bullet dest */x, y, time, speed) {
        var startX = this.body.offsetLeft + this.body.offsetWidth / 2 - 2;
        var startY = this.body.offsetTop + this.body.offsetHeight / 2 - 2.5;
        var className = this.body.className;
        
        var bullet = new Bullet(startX, startY, className + "bullet");
        if (className === "enemy") enemyBullet.push(bullet);
        if (className === "player") playerBullet.push(bullet);
        
        var sx = x - startX;
        var sy = y - startY;
        var fact = Math.sqrt(Math.pow(sx, 2) + Math.pow(sy, 2));

        bullet.shoot(sx / fact * speed, sy / fact * speed, time);
    },

    destroy: function() {
        var obj = this.body;
        obj.style.border = "red";
        obj.style.background = "red";
        setTimeout(function() {
            if (obj.parentNode) gameArea.removeChild(obj);
            // delete this;
        }, 200);
    }
};

var Player = function(/*coordinate*/x, y) {
    var self = this;
    var shootInterval = 0;
    var counter = 0;

    var plane = genSquare(16, 16, x, y);

    plane.style.border = "2px solid black";
    plane.className = "player";

    this.xv = defaultV;
    this.yv = defaultV;
    this.body = plane;

    // Define control keys
    keyRegister(function() {
        if (shootInterval === 0)
            self.fire(self.body.offsetLeft + self.body.offsetWidth / 2, 0, 10, 5);
        shootInterval++;
    }, function() { // x skill
        for (var i = 0; i < enemy.length; i++) {
            enemy[i].destroy();
            point += 100;
            update("score", point);
            bonus();
        }
    });
    
    motionTimeId = setInterval(function() {
        // set motion
        moveActionReg({
            onUp:       function() {self.goUp();},
            onDown:     function() {self.goDown();},
            onLeft:     function() {self.goLeft();},
            onRight:    function() {self.goRight();},
            onSlow:     function() {self.xv = 1;self.yv = 1;},
            onNormal:   function() {self.xv = defaultV;self.yv = defaultV;}
        });

        counter++;
        if (counter == 10) {
            shootInterval = 0;
            counter = 0;
        }
    }, 10);
};

Player.prototype = planeProto;
Player.prototype.goUp = function() {
    var newPos = this.body.offsetTop - this.yv;
    if (newPos >= 0) this.moveUp();        
};

Player.prototype.goDown = function() {
    var newPos = this.body.offsetTop + this.yv + this.body.offsetHeight;
    if (newPos <= window.screen.availHeight - 135)
        this.moveDown();
};

Player.prototype.goLeft = function() {
    var newPos = this.body.offsetLeft - this.xv;
    if (newPos >= 0) this.moveLeft();         
};

Player.prototype.goRight = function() {
    var newPos = this.body.offsetLeft + this.xv + this.body.offsetWidth;
    if (newPos <= window.screen.availWidth)
        this.moveRight();
};


var Enemy = function(/*coordinate*/x, y, /*flying speed*/sx, sy, time, shootTime) {
    var self = this;
    var plane = genSquare(20, 20, x, y);
    var count = 0;

    plane.style.backgroundColor = "black";
    plane.className = "enemy";

    this.body = plane;
    this.xv = sx;
    this.yv = sy;
        
    var timeid = setInterval(function() {
        if (sx > 0) self.moveRight(); else self.moveLeft();
        if (sy > 0) self.moveDown();  else self.moveUp();

        if(detectOutBorder(plane)) {
            clearInterval(timeid);
            self.destroy();
        }
        count++;
        if ( planePlayer && count >= shootTime / time ) {
            self.fire(planePlayer.body.offsetLeft, planePlayer.body.offsetTop, 1, 2);
            count = 0;
        }
    }, time);
};

Enemy.prototype = planeProto;

// --------------------- Generate enemies ----------------------------
var enemyFactory = function(pattern) {
    var pos;
    switch(pattern) {
        case 0: pos = Math.random() * 300;
                enemy.push(new Enemy(0, pos, 5, 1, flyTime, shootTime));
                break;
        case 1: pos = Math.random() * 300;
                enemy.push(new Enemy(1360, pos, -5, 1, flyTime, shootTime));
                break;
        case 2: pos = Math.random() * 1000;
                enemy.push(new Enemy(pos, 0, 0, 5, flyTime, shootTime));
                break;
        case 3: pos = Math.random() * 1000;
                enemy.push(new Enemy(pos, 0, 0, 10, flyTime, shootTime));
                break;
        case 4: pos = Math.random() * 300 + 200;
                enemy.push(new Enemy(0, pos, 5, -1, flyTime, shootTime));
                break;
        case 5: pos = Math.random() * 300 + 200;
                enemy.push(new Enemy(1360, pos, -5, -1, flyTime, shootTime));
                break;
        default:pos = Math.random() * 1000;
                enemy.push(new Enemy(pos, 0, 5, 1, flyTime, shootTime));
                break;
    }
};

/** collision detection */
var collide = function(objA, objB) {
    var a = objA.body, b = objB.body;

    var xa = a.offsetLeft + a.offsetWidth / 2;
    var ya = a.offsetTop + a.offsetHeight / 2;
    var xb = b.offsetLeft + b.offsetWidth / 2;
    var yb = b.offsetTop + b.offsetHeight / 2;
    var dist = Math.sqrt(Math.pow(xa - xb, 2) + Math.pow(ya - yb, 2));
    return dist <= 15;
};

/** get all target object and detect collision */
var collideDetect = function() {
    detectCollision({
        player:                 planePlayer,
        enemy:                  enemy,
        enemyBullet:            enemyBullet,
        playerBullet:           playerBullet,
        detectCollide:          collide,
        gameOver:               function() {
            planePlayer = undefined;
            gameOver(domGameInit);            
        },
        gameRestart:            function() {
            planePlayer = new Player(650, 550);
        }
    });
};


// ***************************Game Initialize*************************
var domGameInit = function() {
    planePlayer = new Player(650, 550);
    gameInit(collideDetect, enemyFactory);
};

return domGameInit;

}());


var canvasGame = (function() {

var canvas, ctx;
var width = 1370, height = 570;
var bigLen = 20, smallLen = 5;
var defaultV = 5;
var gameTimeId;

var originalPlayer = [700, 550, defaultV, defaultV];
var playerPos, enemyPos = [];
var playerBulletPos = [], enemyBulletPos = [];

var clearRect = function(pos, len) {
    ctx.clearRect(pos[0] - 1, pos[1] - 1, len + 2, len + 2);
};

Array.prototype.destroy = function() {
    var self = this;
    clearRect(this, bigLen);
    ctx.fillStyle = "red";
    this[2] = this[3] = 0;
    clearInterval(self.fireInterval);
    ctx.fillRect(this[0], this[1], 10, 10);
    ctx.fillStyle = "black";
    setTimeout(function() {clearRect(self, 10);}, 200);
};

Array.prototype.enemyFire = function() {
    var self = this;
    self.fireInterval = setInterval(function() {
        var sx = playerPos[0] - self[0];
        var sy = playerPos[1] - self[1];
        var fact = Math.sqrt(Math.pow(sx, 2) + Math.pow(sy, 2));
        enemyBulletPos.push([self[0] + 7, self[1] + 9, sx / fact * 2, sy / fact * 2]);
    }, shootTime);
};

var isCollide = function(pos1, pos2) {
    var distance = Math.sqrt( Math.pow(pos1[0] - pos2[0], 2) + 
            Math.pow(pos1[1] - pos2[1], 2) );
    if (distance < 20) return true;
    return false;
};

var isOutBorder = function(pos) {
    return pos[0] > width || pos[1] > height || pos[0] < 0 || pos[1] < 0;
};

// Drawing function
var drawPlayer = function() {
    if (!isOutBorder(playerPos))
        ctx.strokeRect(playerPos[0], playerPos[1], bigLen, bigLen);
};

var clearPlayer = function() {
    clearRect(playerPos, bigLen);
};

var drawAutoMoving = function(posArray, method, len) {
    if (posArray.length === 0) return;
    var tmpArray = posArray.slice(0);
    for (var i = 0; i < tmpArray.length; i++) {
        clearRect(tmpArray[i], len);
        if (tmpArray[i][2] === 0 && tmpArray[i][3] === 0) {
            posArray.splice(i, 1);
            continue;
        }
        tmpArray[i][0] += tmpArray[i][2];
        tmpArray[i][1] += tmpArray[i][3];
        if (isOutBorder(tmpArray[i])) {
            posArray.splice(i, 1);
        } else {
            ctx[method](tmpArray[i][0], tmpArray[i][1], len, len);            
        }
    }
};

var drawEnemy = function() {
    drawAutoMoving(enemyPos, "fillRect", bigLen);
};

var drawEnemyBullet = function() {
    drawAutoMoving(enemyBulletPos, "fillRect", smallLen);
};

var drawPlayerBullet = function() {
    drawAutoMoving(playerBulletPos, "strokeRect", smallLen);
};

var collisionDetect = function() {
    detectCollision({
        player:                 playerPos,
        enemy:                  enemyPos,
        enemyBullet:            enemyBulletPos,
        playerBullet:           playerBulletPos,
        detectCollide:          isCollide,
        gameOver:               function() {
            playerPos = [];
            gameOver(canvasGameInit);            
        },
        gameRestart:            function() {
            playerPos = originalPlayer.slice(0);
            drawPlayer();
        }
    });   
};

var generatePlayer = function() {
    var playerCounter = 0, shootInterval = 0;
    var moveFunc = function(moveType) {
        var tempPos = playerPos.slice(0);
        switch( moveType ) {
            case "up":    tempPos[1] = playerPos[1] - playerPos[3]; break;
            case "down":  tempPos[1] = playerPos[1] + playerPos[3]; break;
            case "left":  tempPos[0] = playerPos[0] - playerPos[2]; break;
            case "right": tempPos[0] = playerPos[0] + playerPos[2]; break;
        }

        if ( !isOutBorder(tempPos) ) {
            clearPlayer();
            playerPos = tempPos;
            drawPlayer();
        }
    };

    playerPos = originalPlayer.slice(0);

    drawPlayer();
    keyRegister(function() {
        if (shootInterval === 0) 
            playerBulletPos.push([playerPos[0] + 7, playerPos[1] - 7, 0, -5]);
        shootInterval++;
    }, function() {
        for (var i = 0; i < enemyPos.length; i++) {
            enemyPos[i].destroy();
            point += 100;
            update("score", point);
            bonus();
        }
        enemyPos = [];
    });

    motionTimeId = setInterval(function() {
        // set motion
        moveActionReg({
            onUp:       function() { moveFunc("up"); },
            onDown:     function() { moveFunc("down"); },
            onLeft:     function() { moveFunc("left"); },
            onRight:    function() { moveFunc("right"); },
            onSlow:     function() {playerPos[2] = playerPos[3] = 1;},
            onNormal:   function() {playerPos[2] = playerPos[3] = defaultV;}
        });

        drawEnemyBullet();
        drawPlayerBullet();

        playerCounter++;
        if (playerCounter === 10) {
            shootInterval = 0;
            playerCounter = 0;
        }
    }, 10);
};

var generateEnemy = function(pattern) {
    var pos, enemy;
    switch(pattern) {
        case 0: pos = Math.random() * 300;
                enemy = [0, pos, 5, 1];
                break;
        case 1: pos = Math.random() * 300;
                enemy = [1360, pos, -5, 1];
                break;
        case 2: pos = Math.random() * 1000;
                enemy = [pos, 0, 0, 5];
                break;
        case 3: pos = Math.random() * 1000;
                enemy = [pos, 0, 0, 10];
                break;
        case 4: pos = Math.random() * 300 + 200;
                enemy = [0, pos, 5, -1];
                break;
        case 5: pos = Math.random() * 300 + 200;
                enemy = [1360, pos, -5, -1];
                break;
        default:pos = Math.random() * 1000;
                enemy = [pos, 0, 5, 1];
                break;
    }
    enemyPos.push(enemy);
    enemy.enemyFire();
};

var canvasGameInit = function() {
    canvas = doc.byId("canvasarea");
    canvas.width = width;
    canvas.height = height;
    enemyPos = [];
    playerBulletPos = [];
    enemyBulletPos = [];
    point = 0;  // Game score, last time set
    jet = 2;
    bomb = 2;

    ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, width, height);
    generatePlayer();
    gameInit(collisionDetect, generateEnemy);

    clearInterval(gameTimeId);
    gameTimeId = setInterval(drawEnemy, flyTime);
};

return canvasGameInit;

}());


// page related, not game related
var init = function() {
    var $gameSelect = doc.byId("game-type"),
        $help = doc.byId("helpinfo"),
        $gametype = doc.byId("game-type"),
        $menu = doc.byId("menu");

    gameArea  = doc.byId("domarea");

    var hidePopup = function() {
        for (var index = 0; index < arguments.length; index++) {
            if (arguments[index].style.display === "block") {
                arguments[index].style.display = "none";
                $menu.style.opacity = 1;
            }
        }      
    };

    var showPopup = function(popupNode) {
        if (!popupNode.style.display ||
            popupNode.style.display === "none") {
            popupNode.style.display = "block";
            $menu.style.opacity = 0.15;
        }        
    };

    doc.byId("start").onclick = function() {
        util.stopBubble(event);
        showPopup($gametype);
    };

    $gameSelect.onclick = function(event) {
        if (event.target.tagName !== "LI") return;
        var id = event.target.id;

        doc.byId("title").style.display = "none";
        doc.byId("game").style.display = "block";

        if (id === "dom") {
            doc.byId("domarea").style.display = "block";
            domGame();
        } else if (id === "canvas") {
            doc.byId("canvasarea").style.display = "block";
            canvasGame();
        }
    };

    doc.byId("help").onclick = function() {
        util.stopBubble(event);
         // Show help 
        showPopup($help);
    };

    // Hide help 
    document.onclick = function() {
        hidePopup($help, $gametype);
    };

    doc.byId("exit").onclick = function() {
        // * Exit confirm 
        if (confirm("Quit the game?")) close();
    };

    doc.byId("back").onclick = function() {
        var flag = confirm("Abort the game?");
        if (flag) {
            var title = doc.byId("title");
            var game = doc.byId("game");
            var body = doc.byTag("body")[0];
            body.removeChild(game);
            title.style.display = "block";
            clearInterval(detectId);
        }
        return flag;
    };
};

return init;

});