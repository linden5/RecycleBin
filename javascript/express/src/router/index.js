const express = require('express');
const router = express.Router();
const modelInit = require('../db/model');

router.get('/', function(req, res) {
    modelInit.then(function (Model) {
        let Blog = Model.Blog;

        Blog.find({}, function (err, blog) {
            if (err) {
                throw err;
            } else {
                res.render('index', {blogs: blog});
            }
        });
        //Blog.create({title: 'first title', content: 'first content', create_at: new Date(), modified_at: new Date()}, function(err, items) {
        //   console.log('created');
        //});
    });
});

module.exports = router;

