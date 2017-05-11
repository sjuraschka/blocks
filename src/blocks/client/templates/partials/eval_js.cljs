(ns blocks.client.templates.partials.eval-js)

(defn eval-js-component [js-code]
  [:div {:ref (fn [_]
                (js/eval js-code))}])

