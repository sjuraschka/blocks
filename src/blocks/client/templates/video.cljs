(ns blocks.client.templates.video
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.video
   {:padding "4em"
    :background (get-in data [:styles :background])}

   [:.content
    {:display "flex"
     :flex-direction  "column"
     :justify-content "center"
     :align-items "center"
     :text-align "center"}

     [:h1
      {:color (get-in data [:heading :color])
       :font-size "2.2rem"
       :font-weight "bolder"
       :position "relative"
       :z-index 0}]
     [:h2
      {:color (get-in data [:subtitle :color])
       :font-size "1.25em"
       :padding-bottom "2em"}]

     [:.player
      {:max-height "20em"}]
    [:p {:padding "1em"
         :color "#fff"}]]])

(defn component [data]
  [:div.video
   [:div.content
     [:h1 (get-in data [:heading :title])]
     [:h2 (get-in data [:subtitle :text])]
     [:img.player {:src "/images/video-player.png"}]
     ;[:p "We hated chat."]
    ]])

(defmethod template "video" [_]
  {:css       styles
   :component component})

