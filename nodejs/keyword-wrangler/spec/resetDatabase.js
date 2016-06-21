"use strict";

var async = require("async");
var env = require("../src/backend/env");
var dbOptions = require("../database.json")[env];

var resetDatabase = function(dbSession, callback) {
    if (dbOptions.driver === "sqlite3") {
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
                function(callback) {
                    console.log("remove sqlite_sequence");
                    dbSession.remove("sqlite_sequence", "1", function(err) {
                        console.log("remove sqlite_sequence finish");
                        callback(err);
                    });
                }
            ],

            function(err, results) {
                console.log("Reset finish:", results);
                callback(err);
            }
        );        
    }

    if (dbOptions.driver === "mysql") {
        async.series(
            [
                function(callback) {
                    console.log("remove keyword");
                    dbSession.remove("TRUNCATE keyword", [], function(err) {
                        console.log("remove keyword finish");
                        callback(err);
                    });
                },
                function(callback) {
                    console.log("remove category");
                    dbSession.remove("TRUNCATE category", [], function(err) {
                        console.log("remove category finish");
                        callback(err);
                    });
                }
            ],

            function(err, results) {
                console.log("Reset finish:", results);
                callback(err);
            }
        );        
    }
};

module.exports = resetDatabase;