((global, factory) => {
    if (global.document) {
        factory(global)
    } else {
        console.error("This self implemented require system can only be used in browser environment.")
    }
})(window || this, (global) => {
    var modules = {};
    var modNames = [];

    function isArray(obj) {
        return Object.prototype.toString.call(obj) === "[object Array]";
    }

    function loadDeps(deps) {
        var depVars = [];
        if (deps && deps.length > 0) {
            deps.forEach((dep) => {
                depVars.push(requireSingle(dep));
            });
        }

        return depVars;
    }

    function addScriptPostFix(name) {
        if (name.indexOf(".js") === -1)
            name += ".js";
        return name;
    }

    // The length of arguments may vary from 1 to 3 
    function define() {
        var name, deps, factory;
        var args = Array.prototype.slice.apply(arguments);

        if (args.length === 3) {
            name = args[0];
            deps = args[1];
            factory = args[2];
        } else if (args.length === 2) {
            if (typeof args[0] === "string") {
                name = args[0];
                factory = args[1];
            } else if (isArray(args[0])) {
                deps = args[0];
                factory = args[1];
            } else {
                console.error("The first argument must be string or array");
                return;
            }
        } else if (args.length === 1) {
            factory = args[0];
        }

        if (typeof name !== "string") {
            console.error("The name of the module must be string");
            console.error("Current name:" + name);
            return;
        }

        if (deps && !isArray(deps)) {
            console.error("The dependencies must be given by an array of their names");
            console.error("Current deps:" + deps.toString());
            return;
        }

        var depVars = loadDeps(deps);

        var facType = typeof factory;
        if (facType !== "function" && facType !== "object") {
            console.error("You can only define a module by passing an object or a factory function.");
            return;
        }

        var modReturn;
        if (facType === "object") {
            modReturn = factory;
        } else if (facType === "function") {
            modReturn = factory.apply(this, depVars);
        }

        if (name)
            modules[addScriptPostFix(name)] = modReturn;
        else
            modules[modNames.shift()] = modReturn;
    }

    function requireSingle(name) {
        var moduleName = addScriptPostFix(name);

        if (modules[moduleName]) {
            return modules[moduleName];
        } else {
            var script = document.createElement("script");
            script.setAttribute("type", "text/javascript");
            script.setAttribute("src", moduleName);
            document.body.appendChild(script);
            modNames.push(moduleName);
        }
    }

    // The arguments length vary from 1 to 2
    function require() {
        var deps, factory;
        var args = Array.prototype.slice.apply(arguments);
        if (args.length === 1) {
            if (typeof args[0] === "function") {
                factory = args[0];
            } else if (isArray(args[0])) {
                deps = args[0];
            }
            
        } else if (args.length === 2) {
            if (isArray(args[0])) {
                deps = args[0];
            } else if (typeof args[0] === "string") {
                deps = [args[0]];
            } else {
                console.error("Dependencies must be passed in in forms of array or string.");
            }

            if (typeof args[1] === "function") {
                factory = args[1];
            }
        }

        window.onload = function() {
            var depVars = loadDeps(deps);
            factory.apply(this, depVars);
        };
    }

    global.require = require;
    global.define = define;
});