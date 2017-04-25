define("index", ["util"], function( util ) {

// Simplify object and method names 
var game,
    gameId,
    gameImg,
    gameLink,
    imgWidth, // The width of 
    pre = 0,// Previously chosen game, default is 0
    animateFlag = false;

// Animate 
// Move an obj to a position
var moveTo = function(obj, finalX, finalY, /* time in millisecond */time) {
    var posX = obj.offsetLeft;
    var posY = obj.offsetTop;
    var timeInterval = 5;
    var stepX = (finalX - posX) / (time / timeInterval);
    var stepY = (finalY - posY) / (time / timeInterval);

    var timeid = setInterval(function() {
        animateFlag = true;
        // X axis
        obj.style.left = motion(stepX, finalX, obj.offsetLeft);
        // Y axis
        obj.style.top = motion(stepY, finalY, obj.offsetTop);
        // Get to destination
        if (obj.offsetLeft === finalX && obj.offsetTop === finalY) {
            animateFlag = false;
            clearInterval(timeid);
        }
    }, timeInterval);
};

// * Handle onclick events for title 
var setChosen = function(event) {
    var node = event.srcElement;
    var gameName = node.name;
    var allChosen = doc.byClass("chosen");
    var chosenGame = doc.byName(gameName);
    var classNameTmp;

    if (animateFlag) return;
    
    animateFlag = true;
    // Set the state of navigators
    while (allChosen.length !== 0) {
        classNameTmp = allChosen[0].className;
        allChosen[0].className = classNameTmp.replace(" chosen", "");
    }

    for (var i = 0; i < chosenGame.length; i++) {
        chosenGame[i].className += " chosen";
    }
    
    // Move the slide
    var imgSlide = doc.byId("image-canvas");
    var y = imgSlide.offsetTop;
    moveTo(imgSlide, - parseInt(gameName) * imgWidth, y, 300);
    
    pre = gameName;
};

var motion = function(step, finals, current) {
    var news = current + step;
    var isReachFinals = (step > 0  && (current >= finals || news > finals)) ||
                        (step <= 0 && (current <= finals || news < finals));

    if (isReachFinals) return [finals, "px"].join("");
    return [news, "px"].join("");
};

var getValueArray = function(key, json) {
    var value = [];
    for (var i = 0; i < json.length; i++) {
        value.push(json[i][key]);
    }
    return value;
};

var handle = function(input) {
    var gameR = JSON.parse(input);
    game = getValueArray("name", gameR);
    gameId = getValueArray("id", gameR);
    gameImg = getValueArray("img", gameR);
    gameLink = getValueArray("link", gameR);
};

// * Game and related resources 
// * Load XML files 
var loadXML = function(file, onSuccess){
    var xmlhttp;
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    if (xmlhttp) {
        // local can only use sync, async doesn't work
        xmlhttp.open("GET", file, false);
//      xmlhttp.onreadystatechange = function (oEvent) {
//          if (xmlhttp.readyState === 4) {
//              if (xmlhttp.status === 200) {
//                  handle(xmlhttp.responseText);
//                  loadGameResources();
//              } else {
//                  console.log("Error", xmlhttp.statusText);
//              }
//          }
//      };
        xmlhttp.send();
        onSuccess(xmlhttp.responseText);
    }
};

// Create a element for page loading
var createLink = function(i, text, link) {
    return ["<a href='", link, "' name='", i, "' class='link'>", text, "</a>"].join("");
};

var createImgDiv = function(gameId, gameLink, gameImg) {
    return ["<a id='", gameId, "' class='img' target='_blank' href='", gameLink, 
            "' style='background-image: url(./images/", gameImg, ")' ></a>"].join("");
};

// Load page 
var loadGameResources = function() {
    var link;
    var img = doc.byId("image-canvas");
    var titleHtml = [],
        imgHtml = [],
        dotHtml = [],
        sideHtml = [];  

    loadXML("./resources/game_resource.json", handle);

    for (var i = 0; i < game.length; i++) {
        link = createLink(i, game[i], "#container");

        // $title
        titleHtml.push( link );
        
        // Side
        sideHtml.push( link );
        
        // Image
        imgHtml.push( createImgDiv(gameId[i], gameLink[i], gameImg[i]) );
        
        // Dots
        dotHtml.push( createLink(i, "", "#container") );
    }

    // Get list nodes
    doc.byId("game-title").innerHTML = titleHtml.join("");
    img.innerHTML = imgHtml.join(""); 
    doc.byId("dots").innerHTML = dotHtml.join("");
    doc.byId("sidebar").innerHTML = sideHtml.join("");

    document.body.onclick = function(e) {
        if (e.srcElement.tagName === "A") setChosen(e);
    };

    imgWidth = doc.byClass("img")[0].offsetWidth;
    img.style.width = [game.length * imgWidth, "px"].join("");
};

return loadGameResources;

});