"use strict";

var http = require("http");
var url = require("url");
var queryString = require("querystring");

http.createServer( function(request, response) {
    var parsedUrl = url.parse(request.url);

    var pathname = parsedUrl.pathname;
    var query = parsedUrl.query;
    var id = queryString.parse(query)[id];

    var result = {
        pathname: pathname,
        id: id,
        value: Math.floor(Math.random() * 100)
    };

    // setTimeout(function() {
        response.writeHead(200, {"Content-type": "application/json"});
        response.end(JSON.stringify(result));
    // }, 2000 + Math.floor(Math.random() * 1000));
}).listen("8080", function() {
    console.log("Echo Server listening on port 8080");
});