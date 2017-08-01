var destructure = require("../src/destructure");

describe('ES6 destructure function test', function() {
    it('function default value of x, y should be 0', function() {
        var result = destructure.destruct1();
        expect(result[0]).toBe(0);
        expect(result[1]).toBe(0);
    });

    it('If you pass in an empty object, x y should be undefined', function() {
        var result = destructure.destruct1({});
        expect(result[0]).toBe(undefined);
        expect(result[1]).toBe(undefined);
    });

    it('function default value of x, y should be undefined', function() {
        var result = destructure.destruct2();
        expect(result[0]).toBe(0);
        expect(result[1]).toBe(0);
    });

    it('If you pass in an empty object, x y should be 0', function() {
        var result = destructure.destruct2({});
        expect(result[0]).toBe(0);
        expect(result[1]).toBe(0);
    });
});