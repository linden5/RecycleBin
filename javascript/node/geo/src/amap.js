const http = require('http');

function get(url, callback) {
    console.log('get', url);
    http.get(url, (res) => {
        const statusCode = res.statusCode;
        const contentType = res.headers['content-type'];

        let error;
        if (statusCode !== 200) {
            error = new Error('Request Failed.\n' +
                            `Status Code: ${statusCode}`);
        } else if (!/^application\/json/.test(contentType)) {
            error = new Error('Invalid content-type.\n' +
                            `Expected application/json but received ${contentType}`);
        }
        if (error) {
            console.log(error.message);
            // consume response data to free up memory
            res.resume();
            return;
        }

        res.setEncoding('utf8');
        let rawData = '';
        res.on('data', (chunk) => rawData += chunk);
        res.on('end', () => {
            try {
                const parsedData = JSON.parse(rawData);
                console.log(parsedData);
                if (typeof callback === 'function') {
                    callback(parsedData);
                }
                
            } catch (e) {
                console.log(e.message);
            }
        });
    }).on('error', (e) => {
        console.log(`Got error: ${e.message}`);
    });
}

function getByAddress(city, address, callback) {
    var url = ['http://restapi.amap.com/v3/geocode/geo?', 
        'key=', '22861e12662471dd17d141b887a2d8cd',
        '&address=', address,
        '&city=', city
    ].join('');
    get(url, callback);
}

function getByOrdinate(longitude, latitude, callback) {
    var url = ['http://restapi.amap.com/v3/geocode/regeo?', 
        'key=', '22861e12662471dd17d141b887a2d8cd',
        '&location=', longitude, ',', latitude,
        '&radius=1000&',
        'extensions=all&batch=false&roadlevel=0'
    ].join('');
    
    get(url, callback);
}

getByAddress('020', encodeURIComponent('南方通信大厦'));
//getByOrdinate(113.44, 42.99);



