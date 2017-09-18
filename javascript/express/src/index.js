const path = require('path');
const express = require('express');
const bodyParser = require('body-parser');
const routerInit = require('./router/router');
const app = express();

app.use(express.static(path.join(__dirname + '/public')));
app.set('view engine', 'pug');
app.set('views', path.join(__dirname + '/views'));
app.use(bodyParser.urlencoded({extended: true}));

routerInit(app);

app.listen(3000, function() {
    console.log('Example app listening on port 3000!');
});