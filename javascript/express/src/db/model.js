const connection = require('./connection');
const blogSetup = require('./blog');
const commentSetup = require('./comment');

var modelInitialize = new Promise(function(resolve, reject) {
    connection.then(function(db) {
        let Model = {
            Blog : blogSetup(db),
            Comment : commentSetup(db)
        };

        resolve(Model);
    }, function(err) {
        console.log('Connect to db failed');
        reject(err);
    });
});


module.exports = modelInitialize;
