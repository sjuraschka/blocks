(ns blocks.client.templates.feature-highlight
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(def pad "4em")

(defn styles [data]
  [:.feature-highlight
   {:padding pad
    :background (get-in data [:background :color])}

   [:.content
    {:display "flex"
     :justify-content "center"
     :flex-wrap "wrap"
     :align-items "center"}

    [:&.right
     [:.text
      {:margin-right (get-in data [:spacing :right])}]]

    [:&.left
     {:flex-direction "row-reverse"}
     [:.text
      {:margin-left (get-in data [:spacing :left])}]]]

   [:.text
    {:min-width "15em"
     :max-width "30em"
     :flex-basis "30%"
     :margin "3em 0"}]

   [:h1
    {:color (get-in data [:heading :color])
     :font-size (get-in data [:heading :font-size])
     :letter-spacing (get-in data [:heading :spacer])
     :text-transform (get-in data [:heading :text-transform])
     :margin-bottom "0.5em"
     :position "relative"
     :z-index 0}

    ["&[data-step]::before"
     {:content "attr(data-step)"
      :display "inline-block"
      :position "absolute"
      :top "-1.3em"
      :left "-1.5em"
      :width "2em"
      :height "2em"
      :line-height "2em"
      :text-align "center"
      :background "orange"
      :border-radius "50%"
      :color "white"
      :z-index -1
      :opacity 0.5}]]

   [:.underline
    {:margin-top "-1em"}]

   [:p
    {:color (get-in data [:description :color])
     :font-weight (get-in data [:description :weight])
     :white-space (get-in data [:description :white-space])
     :line-height (get-in data [:description :line-height])}]


   [:img.feature
    {:max-width "32em"
     :margin-top "2em"}]

   (at-media {:max-width "575px"}

     [:img
      {:max-width "25em"}]
     [:.content.left :.content.right
      [:.text
       {:margin-left 0
        :margin-right 0}
       [:h1
        {:font-size "1.5em"}]
       [:p
        {:white-space "normal"}]]])])

(defn component [data]
  [:section.feature-highlight
   [:div.content {:class (get-in data [:image :position])}
    [:div.text
     [:h1 {:data-step (get-in data [:heading :step])}
      (get-in data [:heading :text])]
     [:img.underline {:src (get-in data [:heading :underline])}]

     [:p (get-in data [:description :text])]]
    [:div
     [:img.feature {:src (get-in data [:image :image-url])}]]]])

(defmethod template "feature-highlight" [_]
  {:css styles
   :component component})
