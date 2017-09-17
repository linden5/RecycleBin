const orm = require('orm');
const blogSetup = require('./blog');

const connectionPromise = new Promise(function(resolve, reject) {
    orm.connect('mysql://root:lyxgpdi@localhost:3306/blog', function(err, db) {
        if (err) {
            throw err;
        } else {
            console.log('mysql connection established');
            //let Blog = blogSetup(db);
            //Blog.create({title: 'first title', content: 'first content', create_at: new Date(), modified_at: new Date()}, function(err, items) {
            //   console.log('created');
            //});
            resolve(db);
        }
    });
});

module.exports = connectionPromise;
