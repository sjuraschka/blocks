(ns blocks.client.templates.feature-highlight
  (:require [blocks.client.template :refer [template]]))

(def pad "3em")

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
     {:margin-right "3em"}]]

    [:&.left
     {:flex-direction "row-reverse"}
     [:.text
     {:margin-left "6em"}]]]

   [:.text
     {:min-width "15em"
      :max-width "30em"
      :flex-basis "50%"
      :margin-left "2em"}]

    [:h1
      {:color (get-in data [:heading :color])
       :font-size "2.2em"
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

    [:p
      {:color (get-in data [:description :color])}]

    [:img
      {:max-height "80vh"}]

  ])

(defn component [data]
  [:section.feature-highlight
    [:div.content {:class (get-in data [:image :position])}
      [:div.text
        [:h1 {:data-step (get-in data [:heading :step])} (get-in data [:heading :text])]
        [:p  (get-in data [:description :text])]]
        [:div
      [:img {:src (get-in data [:image :image-url])}]]]])

(defmethod template "feature-highlight" [_]
  {:css styles
   :component component})
