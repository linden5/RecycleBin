"use strict";

var FilesizeWatcher = require("../src/FilesizeWatcher");
var exec = require("child_process").exec;

var path = "D:\\Resource_E\\github_repo\\else_practice\\nodejs\\craftman\\tmp\\filesizewatcher.test";
describe("FilesizeWatcher", function() {
    var watcher;

    afterEach(function() {
        watcher.stop();
    });

    it("should fire a 'grew' event when the file grew in size", function(done) {
        exec("del " + path);
        exec("echo 'a' > " + path, function() {
            watcher = new FilesizeWatcher(path);

            watcher.on("grew", function(gain) {
                expect(gain).toBe(3);
                done();
            });

            exec("echo 'test' > " + path, function() {});
        });
    });

    it("should fire a 'shrank' event when the file decrease in size", function(done) {

        exec("del " + path);
        exec("echo 'test' > " + path, function() {
            watcher = new FilesizeWatcher(path);

            watcher.on("shrank", function(loss) {
                expect(loss).toBe(3);
                done();
            });

            exec("echo 'a' > " + path, function() {});
        });
    });

    it("should fire a 'error' event if path doesn't start with a slash", function(done) {

        path = "afad";
        watcher = new FilesizeWatcher(path);

        watcher.on("error", function(err) {
            expect(err).toBe("Path does not start with a slash");
            done();
        });
    });
});