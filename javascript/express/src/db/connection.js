const orm = require('orm');

const connectionPromise = new Promise(function(resolve, reject) {
    orm.connect('mysql://root:lyxgpdi@localhost:3306/blog', function(err, db) {
        if (err) {
            reject(err);
        } else {
            console.log('mysql connection established');
            resolve(db);
        }
    });
});

module.exports = connectionPromise;
