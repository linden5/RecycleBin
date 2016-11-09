var http = require("http");
var crypto = require("crypto");

function login(query, response) {
    var options = {
        hostname: "112.74.22.44",
        port: 8090,
        path: "/GdpIuserInfo/api/v1/fjplogin",
        method: "post",
        headers: {
            "Content-type": "application/json;charset=utf-8"
        }
    };

    var req = http.request(options, function(res) {
        var body = "";
        res.setEncoding("utf8");
        console.log(res.headers);
        res.on("data", function(chunk) {
            body += chunk;
        }).on("end", function() {
            if (res.statusCode === 200) {
                response.writeHead(200, {"Content-type": "application/json;charset=utf-8"});
            } else {
                response.writeHead(res.statusCode, res.statusMessage, res.headers);
            }

            var result = formatResponse(body);
            console.log(result);
            response.write(JSON.stringify(result));
            response.end();
        });
    });

    var hash = crypto.createHash("md5");
    hash.on("readable", function() {
        var data = hash.read();
        if (data) {
            var token = data.toString("hex");
            console.log(token);

            var reqParam = {
                fn: "loginByTel",
                tel: query.query.userName,
                token: token,
                username: ""
            };

            req.write(JSON.stringify(reqParam));
            req.end();
        }
    });

    hash.write(query.query.password);
    hash.end();
}

function formatResponse(resBody) {
    var status, message;
    var resObject = JSON.parse(resBody);

    if (resObject.errorMsg === "登陆成功") {
        status = 1;
        var result = JSON.parse(resObject.result);

        var message = {
            phoneNo: result.tel,
            IsMonthPay: 0,
            IsApprove: 0,
            name: result.name,
            unitID: "",
            unit: "",
            departMent: ""
        };
    } else {
        status = 0;
        message = "失败";
    }

    return {
        status: status,
        message: message
    };
}

module.exports = login;