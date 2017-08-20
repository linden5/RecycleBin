const path = require('path');
const fs = require('fs');

const extract = require('./extract');
const baseRequest = extract.baseRequest;


const baseUrl = 'http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/';
const filename = 'admin.csv';

var startUrl = baseUrl + 'index.html';

extractProvince(startUrl);

function getBaseUrl(url) {
    var splits = url.split('/');
    splits.pop()
    return splits.join('/') + '/';
}
exports.getBaseUrl = getBaseUrl;


function writeToFile(filename, data, code, parent) {
    fs.appendFile(filename, data + ',' + code + ',' + parent + '\n');
}

function extractProvince(url) {
    baseRequest(url, ($) => {
        let links = $('table.provincetable').find('a');
        let hrefs = [];
        let name = [];

        fs.unlinkSync(filename);

        Array.prototype.slice.apply(links).forEach(function(element, index) {
            hrefs.push(element.attribs.href);
            let data = element.children[0].data;
            name.push(data);
            writeToFile(filename, data, index+1, 0);
        }, this);

        let baseUrl = getBaseUrl(url);

        hrefs.forEach(function(element, index) {
            let suburl = path.dirname(url) + '/' + element;
            extractCity(suburl, index);
        });
    });
}

function extractCity(url, parent) {
    extractLevel(url, parent, 'table.citytable', '.citytr a', extractDistrict);
}

function extractDistrict(url, parent) {
    extractLevel(url, parent, 'table.countytable', '.countytr a', extractTown);
}

function extractTown(url, parent) {
    extractLevel(url, parent, 'table.towntable', '.towntr a', extractVillage)
}

function extractVillage(url, parent) {
    baseRequest(url, ($) => {
        let links = $('table.villagetable').find('.villagetr td');

        let code = [];
        let names = [];
        Array.prototype.slice.apply(links).forEach(function(element, index) {
            if (index % 3 === 0) {
                code.push(element.children[0].data);
            } else if (index % 3 === 2){
                let name = element.children[0].data;
                names.push(name);
                writeToFile(filename, name, code[code.length - 1], parent);
            }
        }, this);
    });
}

function extractLevel(url, parent, tableSelector, rowSelector, extractNextLevel) {
    baseRequest(url, ($) => {
        let links = $(tableSelector).find(rowSelector);

        let hrefs = [];
        let code = [];
        let names = [];

        Array.prototype.slice.apply(links).forEach(function(element, index) {
            if (index % 2 === 0) {
                hrefs.push(element.attribs.href);
                code.push(element.children[0].data);
            } else {
                let name = element.children[0].data;
                names.push(name);
                writeToFile(filename, name, code[code.length - 1], parent);
            }
        }, this);

        if (extractNextLevel && typeof extractNextLevel === 'function') {
            hrefs.forEach(function(element, index) {
                let suburl = path.dirname(url) +'/'+ element;
                extractNextLevel(suburl, code[index]);
            });
        }
    });
}