const path = require('path');
const express = require('express');
const app = express();

app.use(express.static(path.join(__dirname + '/public')));
app.set('view engine', 'pug');
app.set('views', path.join(__dirname + '/views'));

app.get('/', function(req, res) {
    res.render('index');
});

app.get('/blog', function(req, res) {
    res.render('blog');
});

app.get('/editor', function(req, res) {
    res.render('editor');
});

app.get('/editor', function(req, res) {
    res.render('editor');
});

app.listen(3000, function() {
    console.log('Example app listening on port 3000!');
});