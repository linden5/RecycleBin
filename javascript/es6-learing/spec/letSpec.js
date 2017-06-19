var letTest = require('../src/let.js');
console.log(letTest)
describe('using let and using var should have deferrent output', function() {
    it('using var has result 10', function() {
        expect(letTest.varTest()[6]()).toBe(10);
    });

    it('using var has result 6', function() {
        expect(letTest.letTest()[6]()).toBe(6);
    });
});