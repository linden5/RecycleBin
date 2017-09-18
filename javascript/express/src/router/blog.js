const express = require('express');
const router = express.Router();
const modelInit = require('../db/model');

router.param('id', function(req, res, next, id) {
    req.blogId = id;
    next();
});

router.get('/:id', function(req, res) {
    modelInit.then(function (Model) {
        let Blog = Model.Blog;

        Blog.find({id: req.blogId}, function (err, blog) {
            if (err) {
                throw err;
            } else {
                res.render('blog', {blog: blog[0]});
            }
        });
    });
});

router.post('/save', function(req, res) {
    var title = decodeURIComponent(req.body.title);
    var content = decodeURIComponent(req.body.content);
    var date = new Date();
    modelInit.then(function(Model) {
        Model.Blog.create({title: title, content: content, create_at: date, modified_at: date}, function(err, items) {
           if (err) {
               res.sendStatus(500);
           } else {
               res.sendStatus(200);
           }
        });
    });
});

module.exports = router;

