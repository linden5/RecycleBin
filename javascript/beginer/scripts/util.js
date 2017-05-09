define("util", function($) {

var hasClass = function(oneClassName, classNames) {
    for (var j = 0; j < classNames.length; j++) {
        if(classNames[j] === oneClassName){
            return true;
        }
    }

    return false;
};

// Following are exposed methods
var nativeJSHelperInit = function() {
    document.byId = document.getElementById;
    document.byName = document.getElementsByName;
    document.byTag = document.getElementsByTagName;

    // Make it compatible with ie, 
    // source:http://www.cnblogs.com/duanhuajian/archive/2013/03/06/2946584.html
    // ========================================
    // root：父节点，tagName：该节点的标签名。 这两个参数均可有可无
    document.byClass = function(className, rootElem, tagName) { 
        var tag, tagAll = [];

        tagName = tagName || "*";

        if(rootElem){
            rootElem = typeof rootElem === "string" ? document.getElementById(rootElem) : rootElem;   
        }else{
            rootElem = document.body;
        }

        //如果浏览器支持getElementsByClassName，就直接的用
        if (document.getElementsByClassName) {                    
            return rootElem.getElementsByClassName(className);
        }else { 
            tag = rootElem.getElementsByTagName(tagName);    //获取指定元素

            for (var i = 0; i < tag.length; i++) { 
                var classNames = tag[i].className.split(' ');
                if ( hasClass(className, classNames) ) {              //遍历获得的元素
                    tagAll.push(tag[i]);
                    break;
                }
            }

            return tagAll;
        }
    };

    return document;
};

// * Stop bubble, ref = http://blog.csdn.net/xxd851116/article/details/4234188 
var stopBubble = function(e) {  
    var event = e ? e : window.event;  
    if (window.event) { // IE  
        event.cancelBubble = true;   
    } else { // FF  
        event.stopPropagation();   
    }   
};

// not exposed
var URLParamToJSON = function() {
    var keyValue = [], 
        paramJSON = {}, 
        paramPair = location.search.substring(1).split("&");

    for (var paramIndex = 0; paramIndex < paramPair.length; paramIndex++) {
        keyValue = paramPair[paramIndex].split("=");
        paramJSON[ keyValue[0] ] = keyValue[1];
    }

    return paramJSON;
};

var JSONParse = function(JSONString) {
    var obj = {};
    var begin = JSONString.indexOf("{");
    var end = JSONString.indexOf("}");
    var keyValueString = JSONString.substring(begin + 1, end);

    var keyValues = [];
    var cur, pivot = 0, tmpStr = keyValueString.replace(" ", "");
    for (cur = 0; cur < tmpStr.length; cur++) {
        if (tmpStr[cur] === "," &&
            tmpStr[cur - 1] === "\"" && 
            tmpStr[cur + 1] === "\"") {
            keyValues.push(tmpStr.substring(pivot, cur));
            pivot = cur + 1;
        }
    }

    keyValues.forEach(function(pair) {
        var map = pair.split("\"");
        var colon = map.indexOf(":");
        var key = map.slice(1, colon).join("");
        var value = map.slice(colon + 1, map.length - 1).join("");
        obj[key] = value;
    });

    return obj;
}

return {
    nativeJSHelperInit:     nativeJSHelperInit,
    stopBubble:             stopBubble
}; 

});