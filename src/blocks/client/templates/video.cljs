(ns blocks.client.templates.video
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.video
   {}
   [:.content
    {:display "flex"
     :justify-content "space-around"
     :padding "2em"}
    [:.left {}
     [:h1
      {:color "#000"
       :font-size "2.2em"
       :margin-bottom "0.5em"
       :position "relative"
       :z-index 0}]
     [:h2
      {}]]
    [:.right {}
     [:.player]]]])

(defn component [data]
  [:div.video
   [:div.content
    [:div.left
     [:h1 (get-in data [:title])]
     [:h2 (get-in data [:subtitle])]]
    [:div.right
     [:iframe.player {:src  "https://www.youtube.com/embed/pa2bUsChFqM"
                     :style {:height "252px"
                             :width "448px"
                             :frameborder 0}}]]]])

(defmethod template "video" [_]
  {:css       styles
   :component component})

