"use strict";

var mysql       = require("mysql"),
    http        = require("http"),
    url         = require("url"),
    querystring = require("querystring");

// Start a web server on port 8888, Requests go to function handleRequest

http.createServer(handleRequest).listen(8888);

// Function that handles http requests

function handleRequest(request, response) {
    // Page html as one big string, with placeholder "DBCONTENT" for data from the database
    var pageContent = "<html>" +
                      "<head>" +
                      "<meta http-equiv='Content-type' " +
                      "content='text/html'; charset='utf-8' />" +
                      "</head>" +
                      "<body>" +
                      "<form action='/add' method='post'>" +
                      "<input type='text' name='content'>" +
                      "<input type='submit' value='Add content' />" +
                      "</form>" +
                      "<div>" +
                      "<strong>Content in database:</strong>" +
                      "<pre>" +
                      "DBCONTENT" +
                      "</pre>" +
                      "</div>" +
                      "<form action='/' method='get'>" +
                      "<input type='text' name='q'>" +
                      "<input type='submit' value='Filter content' />" +
                      "</form>" +
                      "</body>" +
                      "</html>";

    // Parsing the requested url path in order to distinguish between the "/" page and the "/add" route
    var pathname = url.parse(request.url).pathname;

    // User want to add content to the database(Post request to /add)
    if (pathname == "/add") {
        var requestBody = "";
        var postParameters;
        request.on("data", function(data) {
            requestBody += data;
        });

        request.on("end", function() {
            postParameters = querystring.parse(requestBody);
            // The content to be added is in POST parameter "content"
            addContentToDatabase(postParameters.content, function() {
                // Redirect back to home page when the database has finished adding
                response.writeHead(302, {"Location": "/"});
                response.end();
            });
        });
    // user want to read data from the database
    } else {
        // The text to use for filtering is in Get parameter "q"
        var filter = querystring.parse(url.parse(request.url).query).q;
        getContentsFromDatabase(filter, function(contents) {
            response.writeHead(200, {"Content-type": "text/html"});
            // Poor man's templating system: Replace "DBCONTENT" in page Html
            // with the actual content we received from the database
            response.write(pageContent.replace("DBCONTENT", contents));
            response.end();
        });
    }
}

// Function that is called by the code that handles the / route and 
// retrieves contents from the database, applying a LIKE filter if one is provided

function getContentsFromDatabase(filter, callback) {
    var connection = mysql.createConnection({
        host: "localhost",
        user: "root",
        password: "lyxgpdi",
        database: "node"
    });

    var query;
    var resultAsString = "";

    if (filter) {
        filter = filter + "%";
        query = connection.query("SELECT id, content FROM test " +
                                 "WHERE content LIKE ?", filter);
    } else {
        query = connection.query("SELECT id, content FROM test");
    }

    query.on("error", function(err) {
        console.log("A database error occured");
        console.log(err);
    });

    // With every result, build the string that is later replaced into
    // the html of the homepage
    query.on("result", function(result) {
        resultAsString += "id: " + result.id;
        resultAsString += ", content: " + result.content;
        resultAsString += "\n";
    });

    query.on("end", function(result) {
        connection.end();
        callback(resultAsString);
    });
}

// Function that is called by the node that handles the /add route
// and inserts the supplied string as a new content entry

function addContentToDatabase(content, callback) {
    var connection = mysql.createConnection({
        host: "localhost",
        user: "root",
        password: "lyxgpdi",
        database: "node"
    });

    connection.query("INSERT INTO test (content) " +
                     "VALUES (?)", content,
        function(err) {
            if (err) {
                console.log("Could not insert content '" + content +
                            "' into database.");
            }
            callback();
        }
    );    
}
