require.config({
	paths: {
		"jquery": "../lib/jquery-1.11.3"
	}
});

require(["jquery"], function($) {
	$(function() {
		var game;
		var gameId;
		var gameImg;
		var gameLink;
		var animateFlag = false;
		var pre = 0; // Previously chosen game, default is 0
		var imgWidth; // The width of 
		
		/** Game and related resources */
		/** Load XML files */
		loadXML("./gameResource.xml");
		loadGameResources();
		function loadXML(file){
			var xmlhttp = $.ajax({
				url: file,
				dataType: "xml",
				type: "GET",
				async: false,
				error: function(xml) {
					alert("XML loading error!");
				},
				success: function(xml) {
					game = getValueArray($(xml).find("name"));
					gameId = getValueArray($(xml).find("id"));
					gameImg = getValueArray($(xml).find("img"));
					gameLink = getValueArray($(xml).find("link"));
				}
			});
		}

		function getValueArray(nodesArray) {
			var value = [];
			for (var i = 0; i < nodesArray.length; i++) {
				value[i] = $(nodesArray[i]).text();
			}
			return value;
		}

		/* Load page */
		function loadGameResources() {
			var titleNode;
			var imgNode;
			var dotNode;
			var linkNode;
			var sideNode;
			
			// Get list nodes
			var title = $("#gametitle");
			var img = $("#gameimage");
			var dot = $("#dots");
			var sideBar = $("#sidebar");

			for (var i = 0; i < game.length; i++) {
				// Title
				titleNode = $("<td></td>");
				linkNode = createLink(i, game[i], "#container");
				titleNode.append(linkNode);
				title.append(titleNode);
				
				// Side
				sideNode = $("<li></li>");
				linkNode = createLink(i, game[i], "#container");
				sideNode.append(linkNode);
				sideBar.append(sideNode);
				
				// Image
				imgNode = $("<div></div>");
				imgNode.addClass("img");
				imgNode.attr("id", gameId[i]);
				linkNode = $("<a></a>");
				linkNode.attr("href", gameLink[i]);
				linkNode.attr("target", "_blank");
				linkNode.html("<img src='./img/" + gameImg[i] + "' alt='" + game[i] +"' />");
				imgNode.append(linkNode);
				img.append(imgNode);
				
				// Dots
				dotNode = $("<span></span>");
				linkNode = createLink(i, "&nbsp;", "#container");
				dotNode.append(linkNode);
				dot.append(dotNode);
			}
			imgWidth = $($(".img")).width();
			img.width(game.length * imgWidth);
		}

		// Create a element for page loading
		function createLink(i, text, link) {
			var linkNode;
			linkNode = $("<a></a>");
			linkNode.attr("href", link);
			linkNode.attr("name", i);
			linkNode.addClass("unchosen");
			linkNode.click(function() {
				setChosen(this);
			});
			linkNode.html(text);
			return linkNode;
		}

		/** Handle onclick events for title */
		var setChosen = function (target) {
			var node = $(target);
			var gameName = node.attr("name");
			var allChosen = $(".chosen");
			var chosenGame = $("[name =" + gameName + "]");
			if (!animateFlag) {
				animateFlag = true;
				// Set the state of navigators
				for (var i = 0; i < allChosen.length; i++) {
					$(allChosen[i]).attr("class", "unchosen");
				}

				for (var i = 0; i < chosenGame.length; i++) {
					$(chosenGame[i]).attr("class", "chosen");
				}
				
				
				// Move the slide
				var imgSlide = $("#gameimage");
				imgSlide.animate({
					left: - parseInt(gameName) * imgWidth,
				}, 300, function() {
					animateFlag = false;
				});
								
				pre = gameName;
			}
		}
	});	
});	
