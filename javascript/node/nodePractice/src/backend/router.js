var url = require("url");
var file = require("./file");
var controller = require("./controller");

function router (request, response) {
    var pathname = url.parse(request.url).pathname;
    console.log("Request path: '" + pathname);
    if (pathname.indexOf("api") > 0 || pathname.indexOf("airTickets") > 0) {
        controller(request, response);
    } else {
        file(request, response);
    }
}

module.exports = router;