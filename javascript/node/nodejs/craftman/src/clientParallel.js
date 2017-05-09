"use strict";

var request = require("request");
var async = require("async");

var url = "http://localhost:8080/";

async.parallel([
    function(callback) {
        request.get(url + "getUserName?id=1234", function(err, res, body) {
            callback(err, JSON.parse(body).value);
        });
    },
    function(callback) {
        request.get(url + "getUserStatus?id=1234", function(err, res, body) {
            callback(err, JSON.parse(body).value);
        });
    }
],

function(err, results) {
    console.log("The status of user ", results[0], " is ", results[1]);
});