function varFunc() {
    var a = [];
    for ( var i = 0; i < 10 ; i++) {
        a[i] = function() {
            return i;
        };
    }

    return a;
}

function letFunc() {
    var a = [];
    for ( let i = 0; i < 10 ; i++) {
        a[i] = function() {
            return i;
        };
    }

    return a;
}

module.exports = {
    varTest: varFunc,
    letTest: letFunc
};