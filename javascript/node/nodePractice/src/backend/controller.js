var http = require("https");
var url = require("url");
var dataBase = require("./database");

function controller(request, response) {
    var reqInfo = url.parse(request.url, true);
    reqInfo.method = request.method;
    var pathname = reqInfo.pathname;
    console.log("Controller path:" + pathname);
    if (pathname === "/airTickets/login") {
        require("./controller/login")(reqInfo, response);
    } else
    if (pathname === "/api/test") {
        writeResponse(response, 200, {"Content-type": "application/json"}, JSON.stringify({status: 0, message: "test"}));
    } else if (pathname === "/api/bestpay") {
        var options = {
            hostname: "findependent.bestpay.com.cn",
            port: 443,
            path: "/zixun/api/v1/homeProductConfig",
            method: "get",
            headers: {
                "Content-type": "application/json;charset=utf-8"
            } 
        };

        var req = http.request(options, function(res) {
            console.log("Status:", res.statusCode);
            console.log("Headers:", res.headers);
            res.setEncoding("utf8");
            res.on("data", function(chunk){
                writeResponse(response, res.statusCode, res.headers, chunk);
            });
            res.on("error", function() {
                writeResponse(response, 500, {"Content-type": "text/plain"}, "Server error.");
            });
        });

        req.on("error", function(e) {
            console.log("Problem with request:", e.message);
        });

        req.write("");
        req.end();
    } else if (pathname === "/api/db") {
        var sql = "SELECT applicationNo, app_name, app_mobile, from_city, to_city FROM t_application WHERE app_status = 3 OR app_status = 4";
        dataBase.query(sql, function(err, rows) {
            if (err) console.log(err);
            else {
                writeResponse(response, 200, {"Content-type": "application/json"}, JSON.stringify(rows));
            }
        });
    } else {
        writeResponse(response, 404, {"Content-type": "text/plain"}, "Method not found");
    }
}

function writeResponse(response, statusCode, header, body) {
    response.writeHead(statusCode, header);
    response.write(body);
    response.end();
}

module.exports = controller;