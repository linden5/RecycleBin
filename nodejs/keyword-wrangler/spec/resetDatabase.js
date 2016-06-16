"use strict";

var async = require("async");

var resetDatabase = function(dbSession, callback) {
    async.series(
        [
            function(callback) {
                console.log("remove keyword");
                dbSession.remove("keyword", "1", function(err) {
                    console.log("remove keyword finish");
                    callback(err);
                });
            },
            function(callback) {
                console.log("remove category");
                dbSession.remove("category", "1", function(err) {
                    console.log("remove category finish");
                    callback(err);
                });
            },
        ],

        function(err, results) {
            console.log("Reset finish:", results);
            callback(err);
        }
    );
};

module.exports = resetDatabase;