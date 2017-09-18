var commands = [{
    "cmd": "bold",
    "icon": "data:image/gif;base64,R0lGODlhFgAWAID/AMDAwAAAACH5BAEAAAAALAAAAAAWABYAQAInhI+pa+H9mJy0LhdgtrxzDG5WGFVk6aXqyk6Y9kXvKKNuLbb6zgMFADs=",
    "desc": "粗体"
}, {
    "cmd": "italic",
    "icon": "data:image/gif;base64,R0lGODlhFgAWAKEDAAAAAF9vj5WIbf///yH5BAEAAAMALAAAAAAWABYAAAIjnI+py+0Po5x0gXvruEKHrF2BB1YiCWgbMFIYpsbyTNd2UwAAOw==",
    "desc": "斜体"
}, {
    "cmd": "underline",
    "icon": "data:image/gif;base64,R0lGODlhFgAWAKECAAAAAF9vj////////yH5BAEAAAIALAAAAAAWABYAAAIrlI+py+0Po5zUgAsEzvEeL4Ea15EiJJ5PSqJmuwKBEKgxVuXWtun+DwxCCgA7",
    "desc": "下划线"
}, {
    "cmd": "createLink",
    "icon": "data:image/gif;base64,R0lGODlhFgAWAOMKAB1ChDRLY19vj3mOrpGjuaezxrCztb/I19Ha7Pv8/f///////////////////////yH5BAEKAA8ALAAAAAAWABYAAARY8MlJq7046827/2BYIQVhHg9pEgVGIklyDEUBy/RlE4FQF4dCj2AQXAiJQDCWQCAEBwIioEMQBgSAFhDAGghGi9XgHAhMNoSZgJkJei33UESv2+/4vD4TAQA7",
    "desc": "创建链接"
}, {
    "cmd": "fontSize",
    "menu": "fontSize",
    "icon": "text-height",
    "desc": "字体大小"
}, {
    "cmd": "fontName",
    "icon": "font",
    "menu": "fontName",
    "desc": "字体"
}, {
    "cmd": "foreColor",
    "menu": "foreColor",
    "icon": "eye",
    "desc": "字体颜色"
},/* {
 "cmd": "copy",
 "icon": "clipboard",
 "desc": "Copies the current selection to the clipboard. Clipboard capability must be enabled in the user.js preference file. See"
 },{
 "cmd": "cut",
 "icon": "scissors",
 "desc": "Cuts the current selection and copies it to the clipboard. Clipboard capability must be enabled in the user.js preference file. See"
 },  {
    "cmd": "delete",
    "icon": "scissors",
    "desc": "Deletes the current selection."
},  {
 "cmd": "heading",
 "val": "h3",
 "icon": "header",
 "desc": "标题"
 },{
    "cmd": "formatBlock",
    "icon": "flash",
    "desc": "Adds an HTML block-style tag around the line containing the current selection, replacing the block element containing the line if one exists (in Firefox, BLOCKQUOTE is the exception - it will wrap any containing block element). Requires a tag-name string to be passed in as a value argument. Virtually all block style tags can be used (eg. \"H1\", \"P\", \"DL\", \"BLOCKQUOTE\"). (Internet Explorer supports only heading tags H1 - H6, ADDRESS, and PRE, which must also include the tag delimiters &lt; &gt;, such as \"&lt;H1&gt;\".)"
}, {
    "cmd": "forwardDelete",
    "icon": "long-arrow-left",
    "desc": "Deletes the character ahead of the cursor's position.  It is the same as hitting the delete key."
}, {
    "cmd": "hiliteColor",
    "val": "Orange",
    "icon": "flask",
    "desc": "Changes the background color for the selection or at the insertion point. Requires a color value string to be passed in as a value argument. UseCSS must be turned on for this to function. (Not supported by Internet Explorer.)"
}, {
    "cmd": "increaseFontSize",
    "icon": "long-arrow-up",
    "desc": "Adds a BIG tag around the selection or at the insertion point. (Not supported by Internet Explorer.)"
}, {
    "cmd": "indent",
    "icon": "indent",
    "desc": "Indents the line containing the selection or insertion point. In Firefox, if the selection spans multiple lines at different levels of indentation, only the least indented lines in the selection will be indented."
}, {
    "cmd": "insertHTML",
    "val": "&lt;h3&gt;Life is great!&lt;/h3&gt;",
    "icon": "code",
    "desc": "Inserts an HTML string at the insertion point (deletes selection). Requires a valid HTML string to be passed in as a value argument. (Not supported by Internet Explorer.)"
}, {
    "cmd": "insertImage",
    "val": "http://dummyimage.com/160x90",
    "icon": "picture-o",
    "desc": "Inserts an image at the insertion point (deletes selection). Requires the image SRC URI string to be passed in as a value argument. The URI must contain at least a single character, which may be a white space. (Internet Explorer will create a link with a null URI value.)"
}, {
    "cmd": "insertOrderedList",
    "icon": "list-ol",
    "desc": "Creates a numbered ordered list for the selection or at the insertion point."
}, {
    "cmd": "insertUnorderedList",
    "icon": "list-ul",
    "desc": "Creates a bulleted unordered list for the selection or at the insertion point."
}, {
    "cmd": "insertParagraph",
    "icon": "paragraph",
    "desc": "Inserts a paragraph around the selection or the current line. (Internet Explorer inserts a paragraph at the insertion point and deletes the selection.)"
}, {
    "cmd": "insertText",
    "val": new Date(),
    "icon": "file-text-o",
    "desc": "Inserts the given plain text at the insertion point (deletes selection)."
}, {
    "cmd": "justifyCenter",
    "icon": "align-center",
    "desc": "Centers the selection or insertion point."
}, {
    "cmd": "justifyFull",
    "icon": "align-justify",
    "desc": "Justifies the selection or insertion point."
}, {
    "cmd": "justifyLeft",
    "icon": "align-left",
    "desc": "Justifies the selection or insertion point to the left."
}, {
    "cmd": "justifyRight",
    "icon": "align-right",
    "desc": "Right-justifies the selection or the insertion point."
}, {
    "cmd": "outdent",
    "icon": "outdent",
    "desc": "Outdents the line containing the selection or insertion point."
}, {
    "cmd": "paste",
    "icon": "clipboard",
    "desc": "Pastes the clipboard contents at the insertion point (replaces current selection). Clipboard capability must be enabled in the user.js preference file. See"
}, {
    "cmd": "redo",
    "icon": "repeat",
    "desc": "Redoes the previous undo command."
}, {
    "cmd": "removeFormat",
    "icon": "eraser",
    "desc": "Removes all formatting from the current selection."
}, {
    "cmd": "selectAll",
    "icon": "circle",
    "desc": "Selects all of the content of the editable region."
}, {
    "cmd": "strikeThrough",
    "icon": "strikethrough",
    "desc": "Toggles strikethrough on/off for the selection or at the insertion point."
}, {
    "cmd": "subscript",
    "icon": "subscript",
    "desc": "Toggles subscript on/off for the selection or at the insertion point."
}, {
    "cmd": "superscript",
    "icon": "superscript",
    "desc": "Toggles superscript on/off for the selection or at the insertion point."
},  {
    "cmd": "undo",
    "icon": "undo",
    "desc": "Undoes the last executed command."
}, {
    "cmd": "unlink",
    "icon": "chain-broken",
    "desc": "Removes the anchor tag from a selected anchor link."
}*/];

var commandRelation = {};

function addClass(className, addName) {
    if (!contains(className)) {
        className = className + ' ' + addName;
    }
    return className;
}

function removeClass(className, removeName) {
    if (contains(className, removeName)) {
        var regex = constructRegex(removeName);
        className = className.replace(regex, '');
    }

    return className;
}

function toggleClass(className, toggleName) {
    if (contains(className, toggleName)) {
        return removeClass(className, toggleName);
    } else {
        return addClass(className, toggleName);
    }
}

function contains(container, containee) {
    var regex = constructRegex(containee);
    return container.match(regex);
}

function constructRegex(word) {
    return new RegExp('\\s' + word + '\\b', 'ig');
}

function getSelectedText() {
    var selection = getSelection();

    // 取得开始的文字
    var anchorNode = selection.anchorNode;
    var anchorOffset = selection.anchorOffset;
    var focusNode = selection.focusNode;
    var focusOffset = selection.focusOffset;

    // 判断选择的方向
    var isForward = isNodeBefore(anchorNode, focusNode);
    var startNode;
    var endNode;
    var startOffset;
    var endOffset;
    if (isForward) {
        startNode = anchorNode;
        endNode = focusNode;
        startOffset = anchorOffset;
        endOffset = focusOffset;
    } else {
        startNode = focusNode;
        endNode = anchorNode;
        startOffset = focusOffset;
        endOffset = anchorOffset;
    }

    var text1 = '';
    if (startNode !== null) {
        var startText = startNode.data;
        text1 = startText.substring(startOffset);
    }

    // 取得结束的文字

    var text2 = '';
    if (endNode !== null) {
        var endText = endNode.data;
        text2 = endText.substring(0, endOffset);
    }



    //取得中间的文字
    var middleText = '';
    if (startNode !== null && endNode !== null) {
        middleText = getMiddleText(startNode, endNode);
    }

    return text1 + middleText + text2;
}

function getMiddleText(startNode, endNode) {
    if (startNode === null && endNode === null) {
        return;
    }

    var pivot = startNode;
    var middleText = '';
    var text;
    while(pivot !== endNode) {
        text = '';
        if (pivot.nextSibling === null) {
            if (pivot.parentNode === null) {
                break;
            } else {
                pivot = pivot.parentNode;
            }
        } else {
            pivot = pivot.nextSibling;
            text = traversal(pivot);
        }
        middleText += text;
    }

    return middleText;

    // 遍历节点选择文字
    function traversal(root) {
        if (pivot === endNode) {
            return '';
        }
        if (root.nodeType === 3) {
            return root.nodeValue;
        } else {
            var text = '';
            var childNodes = root.childNodes;
            for (var i = 0; i < childNodes.length; i++) {
                if (childNodes[i] === endNode) {
                    pivot = endNode;
                    break;
                }
                text += traversal(childNodes[i]);
            }
            return text;
        }
    }
}

function isNodeBefore(startNode, endNode) {
    var startParents = getParents(startNode), endParents = getParents(endNode);
    var count = Math.min(startParents.length, endParents.length);
    var commonParent, startParent, endParent;
    for (var i = 0; i < count; i++) {
        startParent = startParents[startParents.length - i - 1];
        endParent = endParents[endParents.length - i - 1];
        if (startParent === endParent) {
            commonParent = startParent;
        } else {
            var startPos = convertToArray(commonParent.childNodes).indexOf(startParent);
            var endPos = convertToArray(commonParent.childNodes).indexOf(endParent);
            return startPos < endPos;
        }
    }
    return true;
}

function convertToArray(arrayLike) {
    return Array.prototype.slice.apply(arrayLike);
}

function getParents(node) {
    var parents = [];
    if ( node !== null ) {
        var pivot = node;
        while(pivot.parentNode != null) {
            parents.push(pivot.parentNode);
            pivot = pivot.parentNode;
        }
    }

    return parents;
}


function supported(cmd) {
    var css = document.queryCommandSupported(cmd.cmd) ? "btn-succes" : "btn-error"
    return css
}

function doCommand(cmdKey, target) {
    var cmd = commandRelation[cmdKey];
    if (supported(cmd) === "btn-error") {
        alert("execCommand(“" + cmd.cmd + "”)\nis not supported in your browser");
        return;
    }

    if (cmd.menu) {
        var menu = document.getElementById('fontSize');
        menu.style.display = 'block';
        menu.style.left = target.offsetLeft;
    } else {
        document.execCommand(cmd.cmd, false, (cmd.val || getSelectedText()));
    }
}


function statusCheck(buttons) {
    commands.forEach(function(command) {
        var state = document.queryCommandState(command.cmd);
        var className = buttons[command.cmd].className;
        if (state) {
            buttons[command.cmd].className = addClass(className, 'on');
        } else {
            buttons[command.cmd].className = removeClass(className, 'on');
        }
    });
}

function init() {
    var html = [],
        template = '<span><code class="btn btn-xs %btnClass%" title="%desc%"><img class="icon" src="%icon%" data-cmd="%cmd%"/></code></span>';

    commands.map(function(command) {
        commandRelation[command.cmd] = command;
        var temp = template;
        temp = temp.replace(/%icon%/gi, command.icon);
        temp = temp.replace(/%desc%/gi, command.desc);
        temp = temp.replace(/%btnClass%/gi, supported(command));
        temp = temp.replace(/%cmd%/gi, command.cmd);
        html.push(temp);
    });

    // 工具栏按钮点击事件
    var toolbar = document.querySelector(".tool-bar");
    toolbar.innerHTML = html.join('');
    toolbar.addEventListener('mousedown', function(e) {
        e.preventDefault();
        e.stopPropagation();
        if (e.target.tagName === 'IMG') {
            doCommand(e.target.getAttribute('data-cmd'), e.target.parentNode);
            e.target.className = toggleClass(e.target.className, 'on');
        }
    });

    // 使用工具栏按钮
    var buttons = {};
    var allButtons = document.querySelectorAll('img.icon');
    convertToArray(allButtons).map(function(element) {
        buttons[element.getAttribute('data-cmd')] = element;
    });

    // 编辑区域点击事件
    var editor = document.getElementById('editor');
    editor.addEventListener('keyup', function() {
        statusCheck(buttons);
    });
    editor.addEventListener('mouseup', function() {
        statusCheck(buttons);
    });
}

function menuInit() {
    var menus = document.getElementById('menu-list');
    menus.addEventListener('mousedown', function(event) {
        event.preventDefault();
        var target = event.target;
        while (target !== null) {
            if (target.tagName === 'LI') {
                var value = target.value;
                var cmd = target.parentNode.id;
                document.execCommand(cmd, false, value);
                target.parentNode.style.display = 'none';
                break;
            }
            target = target.parentNode;
        }
        return false;
    });

    document.body.addEventListener('mousedown', function() {
        for (var i = 0; i < menus.childNodes.length; i++) {
            if (menus.childNodes[i].style.display === 'block') {
                menus.childNodes[i].style.display = 'none';
            }
        }
    });
}

function submitInit() {
    document.getElementById('submit').onclick = function() {
        var title = document.getElementById('title').value;
        var content = document.getElementById('editor').innerHTML;

        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert(xhr.responseText);
                } else {
                    alert(xhr.status);
                }
            }
        };
        xhr.open('POST', '/blog/save', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var queryString = ['title=', encodeURIComponent(title), '&content=', encodeURIComponent(content)].join('');
        xhr.send(queryString);
    };
}

init();
menuInit();
submitInit();
