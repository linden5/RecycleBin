const baseRequest = require('./extract');

const baseUrl = 'http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/';
var startUrl = baseUrl + 'index.html';

function getBaseUrl(url) {
    var splits = url.split('/');
    splits.pop()
    return splits.join('/') + '/';
}

exports.getBaseUrl = getBaseUrl;

function extractProvice(url) {
    baseRequest(url, ($) => {
        let links = $('table.provincetable').find('a');

        let hrefs = [];
        Array.prototype.slice.apply(links).forEach(function(element) {
            hrefs.push(element.attribs.href);
        }, this);

        let baseUrl = getBaseUrl(url);

        hrefs.forEach(function(element) {
            let url = baseUrl + element;
            extractCity(url);
        });
    });
}

function extractCity(url) {
    baseRequest(url, ($) => {
        let links = $('table.provincetable').find('a');

        let hrefs = [];
        Array.prototype.slice.apply(links).forEach(function(element) {
            hrefs.push(element.attribs.href);
        }, this);

        hrefs.forEach(function(element) {
            extractCity(element);
        });
    });
}