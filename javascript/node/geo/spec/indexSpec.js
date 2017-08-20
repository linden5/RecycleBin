const getBaseUrl = require('../src/index').getBaseUrl;

describe('Extract url dir by splitting with "\\" and then join them ', function() {
    it('url should match', function() {
        let base = 'http://www.baidu.com/';
        expect(getBaseUrl(base + 'aaa')).toBe(base);
    })
});