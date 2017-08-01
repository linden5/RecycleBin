function destructure1({x, y} = {x: 0, y: 0}) {
    return [x, y];
}

function destructure2({x = 0, y = 0} = {}) {
    return [x, y];
}

module.exports = {
    destruct1: destructure1,
    destruct2: destructure2
};