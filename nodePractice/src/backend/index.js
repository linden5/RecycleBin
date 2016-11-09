var http = require("http");

var router = require("./router");

var port = 8000;

var server = http.createServer(router);

server.listen(port);
console.log("Server running at port:" + port);