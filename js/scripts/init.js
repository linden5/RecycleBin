(function() {

var currentPageName = location.pathname.match(/\/(\w+)\./)[1];

// require.config({
//     paths: {
//         util            : "./util",
//         currentPage     : ["./page/", currentPageName].join("")
//     }
// });

require(["util", currentPageName], function(util, currentPage) {
    window.doc = util.nativeJSHelperInit(); 
    currentPage();
});

}());