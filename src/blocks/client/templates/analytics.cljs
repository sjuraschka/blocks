(ns blocks.client.templates.analytics
  (:require
    [reagent.core :as r]
    [blocks.client.template :refer [template]]))

(defn component [data]
  (r/create-class
    {:component-did-mount
     (fn []
       (set! js/GoogleAnalyticsObject "ga")
       (set! js/ga (fn [& args]
                     (set! js/ga.q (or js/ga.q #js []))
                     (.push js/ga.q (clj->js args))))
       (set! js/ga.l (js/Date.))
       (let [a (.createElement js/document "script")
             m (aget (.getElementsByTagName js/document "script") 0)]
         (set! (.-async a) 1)
         (set! (.-src a) "https://www.google-analytics.com/analytics.js")
         (.. m -parentNode (insertBefore a m)))
       (js/ga "create" (data :id) "auto")
       (js/ga "send" "pageview"))
     :reagent-render
     (fn [])}))

(defmethod template "analytics" [_]
  {:css       (fn [_] [])
   :component component})
