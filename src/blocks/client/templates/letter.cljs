(ns blocks.client.templates.letter
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.letter
   {:background (data :background)}
   [:.content
    {:display "flex"
     :flex-direction "column"
     :justify-content "center"
     :align-items "center"
     :padding "3em"}

    [:h1
     {:font-size "1.25em"
      :padding "1em"
      :color (get-in data [:heading :title-color])
      :font-weight "bolder"}]
    [:.text
     {:padding "2em"
      :color (get-in data [:heading :title-color])
      :width :80%
      :column-count 2
      :column-gap "1em"}]]

   (at-media {:max-width "575px"}
             [:.content
              [:.text
               {:column-count 1
                :column-gap 0
                :padding 0
                :text-align "center"}]])])

(defn component [data]
  [:div.letter
   [:div.content
    [:h1 (get-in data [:heading :text])]
    [:div.text (get-in data [:paragraph :text])]]])

(defmethod template "letter" [_]
  {:css       styles
   :component component})