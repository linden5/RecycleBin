const http = require('http');
const url = require('url');
const socketio = require('socket.io');

var player = {
    X: false,
    O: false
};

const server = http.createServer();
const io = socketio(server);

io.on('connection', function(socket) {
    socket.on('login', function(msg) {
        if (!player.X) {
            player.X = true;
            io.emit('login', 'X');
        } else if (!player.O) {
            player.O = true;
            io.emit('login', 'O');
        } else {
            io.emit('login', false);
        }
    });

    socket.on('moveX', function(msg) {
        io.emit('moveX', msg);
    });

    socket.on('moveO', function(msg) {
        io.emit('moveO', msg);
    });
    socket.on('over', function(msg) {
        player.O = false;
        player.X = false;
    });
});

server.on('clientError', (err, socket) => {
    socket.end('HTTP/1.1 400 Bad Request\r\n\r\n');
});

server.listen('8080', () => {
    console.log('Server started');
});
