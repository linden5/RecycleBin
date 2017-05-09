// var exec = require("child_process").exec;
var queryString = require("querystring");
var fs = require("fs");
var formidable = require("formidable");

function start(response) {
    console.log("The request handler 'start' is called.");
    // exec("dir", { timeout: 10000, maxBuffer: 20000 * 1024 }, 
    //     function(error, stdout, stderr) {
    //         console.log("hehe: " + stdout);
    //         response.writeHead(200, {"Content-type": "text/plain"});
    //         response.write(stdout);
    //         response.end();

    // });
    var body = ["<html>", 
        "<head>",
        "<meta http-equiv='Content-type' content='text/html'; ",
        "charset='UTF-8' />",
        "</head>",
        "<body>",
        "<form action='/upload' enctype='multipart/form-data' method='post'>",
        "<input type='file' name='upload' multiple='multiple' />",
        "<input type='submit' value='Upload file' />",
        "</form>",
        "</body>",
        "</html>"].join("");

    response.writeHead(200, {"Content-type": "text/html"});
    response.write(body);
    response.end();
}

function upload(response, request) {
    console.log("The request handler 'upload' is called.");
    var dir = __dirname + "/tmp";
    var destPath = dir + "/test.png";

    var form = new formidable.IncomingForm();
    form.uploadDir = dir;
    console.log("About to parse");

    form.parse(request, function(error, fields, files) {
        console.log("parsing done");

        fs.rename(files.upload.path, destPath, function(error) {
            // Handle windows error
            if (error) {
                console.log("file rename error:" + error.toString());
                fs.unlink(destPath);
                fs.rename(files.upload.path, destPath);
            }
        });
        response.writeHead(200, {"Content-type": "text/html"});
        response.write("received image: <br /> ");
        response.write("<img src='/show' />");
        response.end();
    });
}

function show(response) {
    console.log("Request handler 'show' was called.");
    response.writeHead(200, {"Content-type": "image/png"});
    fs.createReadStream("./tmp/test.png").pipe(response);
}

exports.start = start;
exports.upload = upload;
exports.show = show;