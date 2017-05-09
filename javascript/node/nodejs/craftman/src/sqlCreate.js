"use strict";

var mysql = require("mysql");

var connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "lyxgpdi"
});

// connection.query("CREATE DATABASE node", function(err) {
//     if (err) {
//         console.log("Could not create database 'node'.");
//     }
// });

connection.query("USE node", function(err) {
    if (err) {
        console.log("Could not switch to database 'node'.");
    }
});

// connection.query("CREATE TABLE test " +
//                  "(id INT(11) AUTO_INCREMENT, " +
//                  " content VARCHAR(255), " +
//                  " PRIMARY KEY(id))",
//     function(err) {
//         if (err) {
//             console.log("Could not create table 'test'.");
//         }
//     }
// );

connection.query("CREATE TABLE passwords " +
                 "(id INT(11) AUTO_INCREMENT, " +
                 " password VARCHAR(255), " +
                 " PRIMARY KEY(id))",
    function(err) {
        if (err) {
            console.log("Could not create table 'passwords'.");
        }
    }
);

connection.query("INSERT INTO test (content) VALUE ('Hello')");
connection.query("INSERT INTO test (content) VALUE ('World')");

connection.query("INSERT INTO passwords (password) VALUE ('secret')");
connection.query("INSERT INTO passwords (password) VALUE ('dont_tell')");

connection.end();