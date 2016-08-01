function elt (name, attributes) {
    var node = document.createElement(name);
    if (attributes) {
        for (var attr in attributes)
            if (attributes.hasOwnProperty(attr))
                node.setAttribute(attr, attributes[attr]);
    }

    for (var i = 2; i < arguments.length; i++) {
        var child = arguments[i];
        if (typeof child === "string")
            child = document.createTextNode(child);
        node.appendChild(child);
    }

    return node;
}

var controls = Object.create(null);

function createPaint(parent) {
    var canvas = elt("canvas", {width: 500, height: 300});
    var ctx = canvas.getContext("2d");
    var toolbar = elt("div", {class: "toolbar"})

    for (var name in controls) {
        toolbar.appendChild(controls[name](ctx));
    }

    var panel = elt("div", {class: "picturepanel"}, canvas);
    parent.appendChild(elt("div", null, panel, toolbar));
}

var tools = Object.create(null);

controls.tool = function(cx) {
    var select = elt("select");
    for (var name in tools)
        select.appendChild(elt("option", null, name));

    cx.canvas.addEventListener("mousedown", function(event) {
        if (event.which == 1) {
            tools[select.value](event, cx);
            event.preventDefault();
        }
    });

    return elt("span", null, "Tool:", select);
}

function relativePos(event, element) {
    var rect = element.getBoundingClientRect();
    return {x: Math.floor(event.clientX - rect.left),
            y: Math.floor(event.clientY - rect.top)};
}

function trackDrag(onMove, onEnd) {
    function end(event) {
        removeEventListener("mousemove", onMove);
        removeEventListener("mouseup", end);

        if (onEnd)
            onEnd(event);
    }
    addEventListener("mousemove", onMove);
    addEventListener("mouseup", end);
}

tools.Line = function(event, cx, onEnd) {
    cx.lineCap = "round";

    var pos = relativePos(event, cx.canvas);
    trackDrag(function(event) {
        cx.beginPath();
        cx.moveTo(pos.x, pos.y);
        pos = relativePos(event, cx.canvas);
        cx.lineTo(pos.x, pos.y);
        cx.stroke();
    }, onEnd);
};

tools.Erase = function(event, cx) {
    cx.globalCompositeOperation = "destination-out";
    tools.Line(event, cx, function() {
        cx.globalCompositeOperation = "source-over";
    })
};

controls.color = function(cx) {
    var input = elt("input", {type: "color"});
    input.addEventListener("change", function() {
        cx.fillStyle = input.value;
        cx.strokeStyle = input.value;
    });

    return elt("span", null, "Color:", input);
};

controls.brushSize = function(cx) {
    var select = elt("select");
    var sizes = [1, 2, 3, 5, 8, 12, 25, 35, 50, 75, 100];
    sizes.forEach(function(size) {
        select.appendChild(elt("option", {value: size}, size + " pixels"));
    });

    select.addEventListener("change", function() {
        cx.lineWidth = select.value;
    });

    return elt("span", null, "Brush size: ", select);
};

controls.save = function(cx) {
    var link = elt("a", {href: "/"}, "Save");
    function update() {
        try {
            link.href = cx.canvas.toDataURL();
        } catch(e) {
            if (e instanceof SecurityError)
                link.href = "javascript:alert(" + JSON.stringify("Can't save: " + e.toString() + ")");
            else
                throw e;
        }
    }
    link.addEventListener("mouseover", update);
    link.addEventListener("focus", update);
    return link;
};

function loadImageURL(cx, url) {
    var image = document.createElement("img");
    image.addEventListener("load", function() {
        var color = cx.fillStyle, size = cx.lineWidth;
        cx.canvas.width = image.width;
        cx.canvas.height = image.height;
        cx.drawImage(image, 0, 0);
        cx.fillStyle = color;
        cx.strokeStyle = color;
        cx.lineWidth = size;
    });
    image.src = url;
}

controls.openFile = function(cx) {
    var input = elt("input", {type: "file"});
    input.addEventListener("change", function() {
        if (input.files.length == 0) return;
        var reader = new FileReader();
        reader.addEventListener("load", function() {
            loadImageURL(cx, reader.result);
        });
        reader.readAsDataURL(input.files[0]);
    });
    return elt("div", null, "Open file:", input);
};

controls.openURL = function(cx) {
    var input = elt("input", {type: "text"});
    var form = elt("form", null, 
        "Open URL:", input, 
        elt("button", {type: "submit"}, "load"));
    form.addEventListener("submit", function(event){
        event.preventDefault();
        loadImageURL(cx, input.value);
    });
    return form;
};

tools.Text = function(event, cx) {
    var text = prompt("Text", "");
    if (text) {
        var pos = relativePos(event, cx.canvas);
        cx.font = Math.max(7, cx.lineWidth) + "px sans-serif";
        cx.fillText(text, pos.x, pos.y);
    }
};

tools.Spray = function(event, cx) {
    var radius = cx.lineWidth / 2;
    var area = radius * radius * Math.PI;
    var dotsPerTick = Math.ceil(area / 30);

    var currentPos = relativePos(event, cx.canvas);
    var spray = setInterval(function() {
        for (var i = 0; i < dotsPerTick; i++) {
            var offset = randomPointInRadius(radius);
            cx.fillRect(currentPos.x + offset.x,
                        currentPos.y + offset.y, 1, 1);
        }
    }, 25);
    trackDrag(function(event) {
        currentPos = relativePos(event, cx.canvas);
    }, function() {
        clearInterval(spray);
    });
};

function randomPointInRadius(radius) {
    for(;;) {
        var x = Math.random() * 2 - 1;
        var y = Math.random() * 2 - 1;
        if (x * x + y * y <= 1)
            return {x: x * radius, y: y * radius};
    }
}

tools.Rectangle = function(event, cx) {
    var pos = relativePos(event, cx.canvas);
    var oriX = event.clientX;
    var oriY = event.clientY

    var tmpRect = elt("div", 
        {style: "position:absolute;background:" + cx.fillStyle + 
        ";top:" + event.clientY + "px;left:" + event.clientX + "px;"});
    document.body.appendChild(tmpRect);

    var oldPos = pos;
    var endPos, width, height, oldDirX = true, oldDirY = true;

    trackDrag(function(event) {
        endPos = relativePos(event, cx.canvas);
        width = endPos.x - pos.x;
        height = endPos.y - pos.y;

        if (width < 0) {
            oldDirX = false;
            tmpRect.style.left = oriX + width + "px";
        } else if (!oldDirX){
            tmpRect.style.left = oriX + "px";
        }
        if (height < 0) {
            oldDirY = false;
            tmpRect.style.top = oriY + height + "px";
        } else if (!oldDirY){
            tmpRect.style.top = oriY + "px";
        }
        tmpRect.style.width = Math.abs(width) + "px";
        tmpRect.style.height = Math.abs(height) + "px";
    }, function(event) {
        endPos = relativePos(event, cx.canvas);
        cx.fillRect(pos.x, pos.y, endPos.x - pos.x, endPos.y - pos.y);
        document.body.removeChild(tmpRect);
    });
};

tools.ColorPicker = function(event, cx) {
    var pos = relativePos(event, cx.canvas);
    var data;
    try {
        data = cx.getImageData(pos.x, pos.y, 1, 1);
        var colorInput = document.querySelector("input[type=color]");
        colorInput.value = rgbaArray2hex(data.data);
        colorInput.dispatchEvent(new Event("change"));
    } catch (e) {
        alert(e.toString());
    }
};

function rgbaArray2hex(arr) {
    var hexColor = [];
    arr.slice(0, 3).forEach(function(dec) {
        var hex = dec2hex(dec);
        hexColor.push(hex.length < 2 ? "0" + hex : hex); 
    });
    return "#" + hexColor.join("");
}

function dec2hex(dec) {
    if (dec == 0) return "0";
    var map = ["a", "b", "c", "d", "e", "f"]
    var hex = [];
    var remain;
    while(dec > 0) {
        remain = dec % 16;
        remain = remain > 9 ? map[remain % 10]: remain;
        hex.push(remain);
        dec = Math.floor(dec / 16);
    }
    hex = hex.reverse();
    return hex.join("");
}

tools.FloodFill = function(event, cx) {
    var startPos = relativePos(event, cx.canvas);
    var visit = visitor(startPos, cx);
    walk(startPos, visit);
};

function walk(pos, visit) {
    var route = [pos];
    var cur;
    while (route.length > 0) {
        cur = route[route.length - 1];
        if (visit(cur.x + 1, cur.y)) {
            cur = {x: cur.x + 1, y: cur.y};
            route.push(cur);
        } else if (visit(cur.x, cur.y + 1)) {
            cur = {x: cur.x, y: cur.y + 1};
            route.push(cur);
        } else if (visit(cur.x - 1, cur.y)) {
            cur = {x: cur.x - 1, y: cur.y};
            route.push(cur);
        } else if (visit(cur.x, cur.y - 1)){
            cur = {x: cur.x, y: cur.y - 1};
            route.push(cur);
        } else {
            route.pop();
        }
    }
}

function visitor(startPos, cx) {
    var mark = markGetter(cx);
    var pixels =  pixelGetter(cx);
    var pivot = pixels(startPos.x, startPos.y);

    return function(x, y) {
        if (x > cx.canvas.width || y > cx.canvas.height) return false;
        if (x < 0 || y < 0) return false;
        if (mark.get(x, y)) return false;
        mark.set(x, y, true);
        if (equals(pivot, pixels(x, y))) {
            cx.fillRect(x, y, 1, 1);
            return true;
        } else {
                    console.log(pivot);
        console.log(pixels(x, y));
        }
        return false;
    };
}

function markGetter(cx) {
    var mark =[];

    function get(x, y) {
        return mark[x + y * cx.canvas.width];
    }

    function set(x, y, value) {
        mark[x + y * cx.canvas.width] = value;
    }
    return  {
        get: get,
        set: set
    };
}


function pixelGetter(cx) {
    var pixels = cx.getImageData(0, 0, cx.canvas.width, cx.canvas.height);
    return function(x, y) {
        var first = ( x + y * cx.canvas.width ) * 4;
        return pixels.data.slice(first, first + 4);
    };
}

function equals(arr1, arr2) {
    if ( arr1.length < 0 || arr2.length < 0 ) return false;
    if (arr1.length !== arr2.length) return false;
    for (var i = 0; i < arr2.length; i++) {
        if (arr1[i] !== arr2[i]) return false;
    }
    return true;
};

