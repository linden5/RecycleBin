function getTime(date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minite = date.getMinutes();
    var second = date.getSeconds();

    return [[year, month, day].join('-'), [hour, minite, second].join(':')].join(' ');
}

module.exports = getTime;
