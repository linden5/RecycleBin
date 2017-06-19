"use strict";

function varFunc() {
    var a = [];
    for (var i = 0; i < 10; i++) {
        a[i] = function () {
            return i;
        };
    }

    return a;
}

function letFunc() {
    var a = [];

    var _loop = function _loop(i) {
        a[i] = function () {
            return i;
        };
    };

    for (var i = 0; i < 10; i++) {
        _loop(i);
    }

    return a;
}

module.exports = {
    varTest: varFunc,
    letTest: letFunc
};