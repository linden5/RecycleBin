"use strict";

(function() {
    var app = angular.module("app");

    app.factory(
        "RepositoryFactory",
        ["Restangular", "$q", RepositoryFactory]);

    function RepositoryFactory(Restangular, $q) {
        Restangular.setBaseUrl("/api/");

        var Repository = function(options) {
            this.endPoint = options.endPoint;
            this.retrieveItems = options.retrieveItems;
        };

        Repository.prototype.readAll = function() {
            var self = this;
            var deferred = $q.defer();
            Restangular.all(self.endPoint + "/").doGET().then(function(data){
                var items = self.retrieveItems(data);
                deferred.resolve(items);
            });

            return deferred.promise;
        };

        Repository.prototype.createOne = function(newItem) {
            var self = this;
            var deferred = $q.defer();
            Restangular.one(self.endPoint + "/", "").post("", newItem).then(function(response) {
                deferred.resolve(response);
            });
            return deferred.promise;
        };

        Repository.prototype.updateOne = function(item) {
            var self = this;
            var deferred = $q.defer();
            Restangular.one(self.endPoint, item.id).post("", item).then(function(response) {
                deferred.resolve(response);
            });
            return deferred.promise;
        };

        Repository.prototype.deleteOne = function(item) {
            var self = this;
            var deferred = $q.defer();
            Restangular.one(self.endPoint, item.id).remove().then(function(response) {
                deferred.resolve(response);
            });
            return deferred.promise;
        };

        return Repository;
    }
})();