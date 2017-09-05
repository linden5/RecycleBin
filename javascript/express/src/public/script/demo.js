
var parms = [{
    "cmd": "aCommandName",
    "desc": "A DOMString representing the name of the command"
}, {
    "cmd": "aShowDefaultUI",
    "desc": "A Boolean indicating whether the default user interface should be shown. This is not implemented in Mozilla."
}, {
    "cmd": "aValueArgument",
    "desc": "A DOMString representing some commands (such as insertimage) require an extra value argument (the image's url). Pass an argument of null if no argument is needed."
}];
var commands = [{
    "cmd": "bold",
    "icon": "bold",
    "desc": "Toggles bold on/off for the selection or at the insertion point. (Internet Explorer uses the STRONG tag instead of B.)"
}, {
    "cmd": "copy",
    "icon": "clipboard",
    "desc": "Copies the current selection to the clipboard. Clipboard capability must be enabled in the user.js preference file. See"
}, {
    "cmd": "createLink",
    "val": "https://twitter.com/netsi1964",
    "icon": "link",
    "desc": "Creates an anchor link from the selection, only if there is a selection. This requires the HREF URI string to be passed in as a value argument. The URI must contain at least a single character, which may be a white space. (Internet Explorer will create a link with a null URI value.)"
}, {
    "cmd": "cut",
    "icon": "scissors",
    "desc": "Cuts the current selection and copies it to the clipboard. Clipboard capability must be enabled in the user.js preference file. See"
}, {
    "cmd": "delete",
    "icon": "scissors",
    "desc": "Deletes the current selection."
}, {
    "cmd": "fontName",
    "icon": "font",
    "val": "'Inconsolata', monospace",
    "desc": "Changes the font name for the selection or at the insertion point. This requires a font name string (\"Arial\" for example) to be passed in as a value argument."
}, {
    "cmd": "fontSize",
    "val": "1-7",
    "icon": "text-height",
    "desc": "Changes the font size for the selection or at the insertion point. This requires an HTML font size (1-7) to be passed in as a value argument."
}, {
    "cmd": "foreColor",
    "val": "rgba(0,0,0,.5)",
    "icon": "eye",
    "desc": "Changes a font color for the selection or at the insertion point. This requires a color value string to be passed in as a value argument."
}, {
    "cmd": "formatBlock",
    "icon": "flash",
    "desc": "Adds an HTML block-style tag around the line containing the current selection, replacing the block element containing the line if one exists (in Firefox, BLOCKQUOTE is the exception - it will wrap any containing block element). Requires a tag-name string to be passed in as a value argument. Virtually all block style tags can be used (eg. \"H1\", \"P\", \"DL\", \"BLOCKQUOTE\"). (Internet Explorer supports only heading tags H1 - H6, ADDRESS, and PRE, which must also include the tag delimiters &lt; &gt;, such as \"&lt;H1&gt;\".)"
}, {
    "cmd": "forwardDelete",
    "icon": "long-arrow-left",
    "desc": "Deletes the character ahead of the cursor's position.  It is the same as hitting the delete key."
}, {
    "cmd": "heading",
    "val": "h3",
    "icon": "header",
    "desc": "Adds a heading tag around a selection or insertion point line. Requires the tag-name string to be passed in as a value argument (i.e. \"H1\", \"H6\"). (Not supported by Internet Explorer and Safari.)"
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
    "cmd": "italic",
    "icon": "italic",
    "desc": "Toggles italics on/off for the selection or at the insertion point. (Internet Explorer uses the EM tag instead of I.)"
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
}, {
    "cmd": "underline",
    "icon": "underline",
    "desc": "Toggles underline on/off for the selection or at the insertion point."
}, {
    "cmd": "undo",
    "icon": "undo",
    "desc": "Undoes the last executed command."
}, {
    "cmd": "unlink",
    "icon": "chain-broken",
    "desc": "Removes the anchor tag from a selected anchor link."
}];

var commandRelation = {};

function supported(cmd) {
    var css = !!document.queryCommandSupported(cmd.cmd) ? "btn-succes" : "btn-error"
    return css
}

function icon(cmd) {
    return (typeof cmd.icon !== "undefined") ? "fa fa-" + cmd.icon : "";
}

function doCommand(cmdKey) {
    var cmd = commandRelation[cmdKey];
    if (supported(cmd) === "btn-error") {
        alert("execCommand(“" + cmd.cmd + "”)\nis not supported in your browser");
        return;
    }
    val = (typeof cmd.val !== "undefined") ? prompt("Value for " + cmd.cmd + "?", cmd.val) : "";
    document.execCommand(cmd.cmd, false, (cmd.val || ""));
}

function init() {
    var html = '',
        template = '<span><code class="btn btn-xs %btnClass%" title="%desc%" onmousedown="event.preventDefault();" onclick="doCommand(\'%cmd%\')"><i class="%iconClass%"></i></code></span>';
    commands.map(function(command, i) {
        commandRelation[command.cmd] = command;
        var temp = template;
        temp = temp.replace(/%iconClass%/gi, icon(command));
        temp = temp.replace(/%desc%/gi, command.desc);
        temp = temp.replace(/%btnClass%/gi, supported(command));
        temp = temp.replace(/%cmd%/gi, command.cmd);
        html+=temp;
    });
    document.querySelector(".tool-bar").innerHTML = html;
}

init();
