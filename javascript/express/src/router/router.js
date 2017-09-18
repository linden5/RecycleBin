const blogRouter = require('./blog');
const indexRouter = require('./index');

module.exports = function(app) {
    app.get('/', indexRouter);
    app.use('/blog', blogRouter);

    app.get('/editor', function(req, res) {
        res.render('editor');
    });
};
