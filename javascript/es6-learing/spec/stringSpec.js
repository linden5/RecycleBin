var stringTest = require("../src/string");

describe('String template test', function() {
    it('Template with values', function() {
        expect(stringTest.equation()).toBe('1 + 2 * 2 = 5');
    });

    it('Template with function', function() {
        expect(stringTest.fn()).toBe('hello, world');
    });
});