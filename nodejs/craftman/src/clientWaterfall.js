"use strict";

var request = require("request");
var async = require("async");

var url = "http://localhost:8080/";

async.waterfall([
    function(callback) {
        request.get(url + "getSessionId?id=1234", function(err, res, body) {
            callback(null, JSON.parse(body).value);
        });
    },
    function(sId, callback) {
        request.get(url + "getUserId?id=1234", function(err, res, body) {
            callback(null, sId, JSON.parse(body).value);
        });
    },
    function(sId, uId, callback) {
        request.get(url + "getUserName?id=" + uId, function(err, res, body) {
            callback(null, JSON.parse(body).value, sId);
        });
    }
],

function(err, name, sId) {
    console.log("Name:", name);
    console.log("SessionID:", sId);
});