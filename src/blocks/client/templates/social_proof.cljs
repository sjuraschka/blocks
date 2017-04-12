(ns blocks.client.templates.social-proof
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(def pad "2em")

(defn styles [data]
  [:.social-proof
   {:border-top "1px solid rgba(230, 230, 230, 0.25)"
    :box-sizing "border-box"
    :padding pad
    :background (get-in data [:background :color])
    :text-align "center"
    :position "relative"}

   [:.content
    {:display "flex"
     :align-items "center"}

    [:.text
     {:color (get-in data [:heading :color])
      :font-weight "bolder"
      :font-size "0.90em"
      :align-item "flex-start"}]

    [:div.images
     [:.partner-logo
      {:height "1.5rem"
       :margin "0 3em"}]]]
   [:hr
    {:position "absolute"
     :bottom 0
     :left 0
     :right 0
     :width "85vw"
     :border "none"
     :border-bottom "1px solid #eee"}]

   (at-media {:max-width "800px"}
     [:.content
      {:flex-direction "column"}
      [:.text
       {:margin-bottom "1em"}]
      [:div.images
       [:.partner-logo
        {:margin "0 1em"
         :height "1em"}]]])])

(defn component [data]
  [:section.social-proof
   [:div.content
    [:div.text (get-in data [:heading :text])]
    (into
      [:div.images
       (for [partner (data :partners)]
         [:img.partner-logo {:src (partner :image)}])])]
   [:hr]])


(defmethod template "social-proof" [_]
  {:css styles
   :component component})
