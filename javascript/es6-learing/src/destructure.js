"use strict";

function destructure1() {
    var _ref = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : { x: 0, y: 0 },
        x = _ref.x,
        y = _ref.y;

    return [x, y];
}

function destructure2() {
    var _ref2 = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {},
        _ref2$x = _ref2.x,
        x = _ref2$x === undefined ? 0 : _ref2$x,
        _ref2$y = _ref2.y,
        y = _ref2$y === undefined ? 0 : _ref2$y;

    return [x, y];
}

module.exports = {
    destruct1: destructure1,
    destruct2: destructure2
};