/** Simplify object and method names */
var doc = document;
var animateFlag = false;
doc.byId = doc.getElementById;
doc.byName = doc.getElementsByName;
doc.byTag = doc.getElementsByTagName;
//------------------------------------------------------------------------------------------------------------------------------------
// Make it compatible with ie, source:http://www.cnblogs.com/duanhuajian/archive/2013/03/06/2946584.html
doc.byClass = function(className,root,tagName) {    //root：父节点，tagName：该节点的标签名。 这两个参数均可有可无
    if(root){
        root=typeof root=="string" ? document.getElementById(root) : root;   
    }else{
        root=document.body;
    }
    tagName=tagName||"*";                                    
    if (document.getElementsByClassName) {                    //如果浏览器支持getElementsByClassName，就直接的用
        return root.getElementsByClassName(className);
    }else { 
        var tag= root.getElementsByTagName(tagName);    //获取指定元素
        var tagAll = [];                                    //用于存储符合条件的元素
        for (var i = 0; i < tag.length; i++) {                //遍历获得的元素
            for(var j=0,n=tag[i].className.split(' ');j<n.length;j++){    //遍历此元素中所有class的值，如果包含指定的类名，就赋值给tagnameAll
                if(n[j]==className){
                    tagAll.push(tag[i]);
                    break;
                }
            }
        }
        return tagAll;
    }
}
// ----------------------------------------------------------------------------------------------------------------------------------
function swap(a, b) {
	var tmp = a;
	a = b;
	b = tmp;
}

/** Game and related resources */
/** Load XML files */
function loadXML(file){
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new window.XMLHttpRequest();
	} else if (window.ActiveXObject) {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xmlhttp) {
		xmlhttp.open("GET", file, false);
		xmlhttp.send();
	}
    var xmlDoc = xmlhttp.responseXML;
	return xmlDoc;
}

function getValueArray(nodesArray) {
	var value = new Array();
	for (var i = 0; i < nodesArray.length; i++) {
		value[i] = nodesArray[i].childNodes[0].nodeValue;
	}
	return value;
}

var gameR = loadXML("./gameResource.xml");
var game = getValueArray(gameR.getElementsByTagName("name"));
var gameId = getValueArray(gameR.getElementsByTagName("id"));
var gameImg = getValueArray(gameR.getElementsByTagName("img"));
var gameLink = getValueArray(gameR.getElementsByTagName("link"));
var pre = 0; // Previously chosen game, default is 0
var imgWidth; // The width of 

/* Load page */
function loadGameResources() {
	var titleNode;
	var imgNode;
	var dotNode;
	var linkNode;
	var sideNode;
	
	// Get list nodes
	var title = doc.byId("gametitle");
	var img = doc.byId("gameimage");
	var dot = doc.byId("dots");
	var sideBar = doc.byId("sidebar");

	for (var i = 0; i < game.length; i++) {
		// Title
		titleNode = doc.createElement("td");
		linkNode = createLink(i, game[i], "#container");
		titleNode.appendChild(linkNode);
		title.appendChild(titleNode);
		
		// Side
		sideNode = doc.createElement("li");
		linkNode = createLink(i, game[i], "#container");
		sideNode.appendChild(linkNode);
		sideBar.appendChild(sideNode);
		
		// Image
		imgNode = doc.createElement("div");
		imgNode.className = "img";
		imgNode.id = gameId[i];
		linkNode = doc.createElement("a");
		linkNode.href = gameLink[i];
		linkNode.target = "_blank";
		linkNode.innerHTML = "<img src='./img/" + gameImg[i] + "' alt='" + game[i] +"' />";
		imgNode.appendChild(linkNode);
		img.appendChild(imgNode);
		
		// Dots
		dotNode = doc.createElement("span");
		linkNode = createLink(i, "&nbsp;", "#container");
		dotNode.appendChild(linkNode);
		dot.appendChild(dotNode);
	}
	imgWidth = doc.byClass("img")[0].offsetWidth;
	img.style.width = game.length * imgWidth + "px";
}

// Create a element for page loading
function createLink(i, text, link) {
	var linkNode;
	linkNode = doc.createElement("a");
	linkNode.href = link;
	linkNode.name = i;
	linkNode.className = "unchosen";
	linkNode.onclick = setChosen;
	linkNode.innerHTML = text;
	return linkNode;
}

/** Handle onclick events for title */
function setChosen() {
	var node = event.srcElement;
	var gameName = node.name;
	var allChosen = doc.byClass("chosen");
	var chosenGame = doc.byName(gameName);
	if (!animateFlag) {
		animateFlag = true;
		// Set the state of navigators
		while (allChosen.length != 0) {
			allChosen[0].className = "unchosen";
		}

		for (var i = 0; i < chosenGame.length; i++) {
			chosenGame[i].className = "chosen";
		}
		
		
		// Move the slide
		var imgSlide = doc.byId("gameimage");
		var y = imgSlide.offsetTop;
		moveTo(imgSlide, - parseInt(gameName) * imgWidth, y, 300);
		
		pre = gameName;
	}
}

/* Animate */
// Move an obj to a position
function moveTo(obj, finalX, finalY, /* time in millisecond */time) {
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
		if (obj.offsetLeft == finalX && obj.offsetTop == finalY) {
			animateFlag = false;
			clearInterval(timeid);
		}
	}, timeInterval);
}

function motion(step, finals, current) {
	if (step > 0) {
		if (current < finals) {
			var news = current + step;
			if (news > finals)
				return finals + "px";
			else
				return news + "px";
		} else
			return finals + "px";
	} else {
		if (current > finals) {
			var news = current + step;
			if (news < finals)
				return finals + "px";
			else
				return news + "px";
		} else
			return finals + "px";
	}
}