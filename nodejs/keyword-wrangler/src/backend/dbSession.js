"use strict";

var DBWrapper = require("node-dbi").DBWrapper;

var dbWrapper = new DBWrapper("sqlite3", 
    {"path": "D:/Resource_E/github_repo/else_practice/nodejs/keyword-wrangler/tmp/keyword-wrangler.test.sqlite"});

dbWrapper.connect();
module.exports = dbWrapper;