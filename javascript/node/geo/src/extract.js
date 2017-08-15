const http = require('http');
const cheerio = require('cheerio');

function baseRequest(url, callback) {
    http.get(url, (res) => {
        const statusCode = res.statusCode;

        let error;
        if (statusCode !== 200) {
            error = new Error('Failed\n' + `statusCode is ${statusCode}`);
            res.resume();
            return;
        } else {
            let rawData = '';
            res.on('data', (chunk) => {
                rawData += chunk;
            });

            res.on('end', () => {
                $ = cheerio.load(rawData);
                callback($);
            });
        }
    });
}

exports.baseRequest = baseRequest;
