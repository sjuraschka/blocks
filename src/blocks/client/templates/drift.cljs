(ns blocks.client.templates.drift
  (:require
    [blocks.client.template :refer [template]]
    [blocks.client.templates.partials.eval-js :refer [eval-js-component]]))

(defn styles [])

(defn component [data]
  (eval-js-component (str "!function() {\n  var t;\n  if (t = window.driftt = window.drift = window.driftt || [], !t.init) return t.invoked ? void (window.console && console.error && console.error(\"Drift snippet included twice.\")) : (t.invoked = !0, \n  t.methods = [ \"identify\", \"config\", \"track\", \"reset\", \"debug\", \"show\", \"ping\", \"page\", \"hide\", \"off\", \"on\" ], \n  t.factory = function(e) {\n    return function() {\n      var n;\n      return n = Array.prototype.slice.call(arguments), n.unshift(e), t.push(n), t;\n    };\n  }, t.methods.forEach(function(e) {\n    t[e] = t.factory(e);\n  }), t.load = function(t) {\n    var e, n, o, i;\n    e = 3e5, i = Math.ceil(new Date() / e) * e, o = document.createElement(\"script\"), \n    o.type = \"text/javascript\", o.async = !0, o.crossorigin = \"anonymous\", o.src = \"https://js.driftt.com/include/\" + i + \"/\" + t + \".js\", \n    n = document.getElementsByTagName(\"script\")[0], n.parentNode.insertBefore(o, n);\n  });\n}();\ndrift.SNIPPET_VERSION = '0.3.1';\ndrift.load('" (data :id) "');")))

(defmethod template "drift" [_]
  {:css       styles
   :component component})