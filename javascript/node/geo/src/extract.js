const http = require('http');
const cheerio = require('cheerio');
const BufferHelper = require('bufferHelper');
const iconv = require('iconv-lite');

const sleeptime = 3000;

function sleep(timemillis) {
    for (var start = +new Date; +new Date - start <=timemillis;) {

    }
} 

function baseRequest(url, callback) {
    request();

    function request() {
        console.log('try to get:' + url);
        const req = http.get(url, (res) => {
            const statusCode = res.statusCode;
    
            let error;
            if (statusCode !== 200) {
                error = new Error('Failed\n' + `statusCode is ${statusCode}`);
                res.resume();
                return;
            } else {
                let bufferHelper = new BufferHelper();
                res.on('data', (chunk) => {
                    bufferHelper.concat(chunk);
                });
    
                res.on('end', () => {
                    var html = iconv.decode(bufferHelper.toBuffer(), 'gb2312');
                    $ = cheerio.load(html);
                    callback($);
                });
            }
        });

        req.on('error', () => {
            // sleep(sleeptime);
            request();
        });
    }
}

exports.baseRequest = baseRequest;
