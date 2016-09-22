(ns blocks.client.templates.video
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.video
   [:.content
    {:display "flex"
     :justify-content "space-around"
     :padding "2em"}
    [:.left
     {:display         "flex"
      :flex-direction  "column"
      :justify-content "center"}

     [:h1
      {:color (get-in data [:heading :color])
       :font-size "2.2em"
       :margin-bottom "0.5em"
       :position "relative"
       :z-index 0}]
     [:h2
      {:color (get-in data [:subtitle :color])}]]
    [:.right
     [:.player]]]

   (at-media {:max-width "750px"}
             [:.content
              {:display "flex"
               :flex-direction "column"
               :justify-content "center"
               :align-items "center"}
              [:.left
               {:margin-left 0
                :margin-right 0
                :text-align "center"
                :padding "1em"}
               [:h1
                {:font-size "2.2em"}]
               [:h2
                {:color (get-in data [:subtitle :color])}]]])])

(defn component [data]
  [:div.video
   [:div.content
    [:div.left
     [:h1 (get-in data [:heading :title])]
     [:h2 (get-in data [:subtitle :text])]]
    [:div.right
     [:iframe.player {:src  "https://www.youtube.com/embed/pa2bUsChFqM"
                     :style {:height "252px"
                             :width "448px"
                             :frameborder 0}}]]]])

(defmethod template "video" [_]
  {:css       styles
   :component component})

