var mysql = require("mysql");

var pool = mysql.createPool({
    host: "localhost",
    database: "diandian",
    user: "root",
    password: "lyxgpdi"
});

function query (sqlString, callBack) {
    pool.getConnection( function(err, connection) {
        if (err) {
            console.log(err);
            callBack(err);
        } else {
            connection.query(sqlString, callBack);
        }
        connection.release();
    });
}

exports.query = query;