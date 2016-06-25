(ns blocks.client.templates.nav
  (:require [blocks.client.template :refer [template]]))

(defmethod template "nav"
  [_]
  (fn [data]
    [:div.header
     [:div.content
      [:a.logo {:href "/"}
       [:h1 "Braid"]
       [:span.version "beta"]]
      [:div.menu
       (for [link (data :menu)]
         [:a {:href (link :url)
              :class (link :style)}
          (link :text)])]]]))
