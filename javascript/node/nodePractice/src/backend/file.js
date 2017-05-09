var fs = require("fs");
var url = require("url");
var path = require("path");
var mime = require("./mime");

function readFile(request, response) {
    var pathname = url.parse(request.url).pathname;

    if (pathname === "/") mapname = "/index.html";
    else if (pathname.indexOf(".") < 0) mapname = pathname + ".html";
    else mapname = pathname;

    var realPath = "asset" + pathname;
    fs.access(realPath, fs.F_OK, function(err){
        if (err) {
            response.writeHead(404, {"Content-type": "text/plain"});
            response.end(err.toString());            
        } else {
            tryToResponseFile(realPath, response)
        }
    });
}

function tryToResponseFile(realPath, response) {
    fs.stat(realPath, function(err, stats) {
        if (err) {
            console.log(err);
            response.writeHead(500, {"Content-type": "text/plain"});
            response.end(err.toString());
            return;
        }
        if (!stats.isFile()) {
            response.writeHead(404, {"Content-type": "text/plain"});
            response.write(mapname + " was not found on this server.");
            response.end();
        } else {
            fs.readFile(realPath, "binary", function(err, file) {
                if (err) {
                    response.writeHead(500, {"Content-type": "text/plain"});
                    response.end(err.toString());
                } else {
                    response.writeHead(200, {"Content-type": mime.lookup(path.extname(realPath))});
                    response.write(file, "binary");
                    response.end();
                }
            });
        }
    });  
}

module.exports = readFile;