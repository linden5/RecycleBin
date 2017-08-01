var x =1, y = 2;

function equation() {
    return `${x} + 2 * ${y} = ${x + 2 * y}`;
}

function hello() {
    return 'hello';
}
function fn() {
    return `${hello()}, world`;
}

module.exports = {
    equation: equation,
    fn: fn
};