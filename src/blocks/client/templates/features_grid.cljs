(ns blocks.client.templates.features-grid
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  (let [padding "1.5em"]

   [:.features-grid

    [:.content
     {:display "flex"
      :flex-wrap "wrap"
      :justify-content "center"
      :padding padding}]

     [:.feature
      {:padding padding
       :box-sizing "border-box"
       :flex-basis (str (/ 100 (data :columns)) "%")
       :min-width "20em"}

     [:h2
      {:font-weight "bold"
       :color (get-in data [:styles :heading-color])
       :margin-bottom "0.25em"}]

      [:p {
        :color (get-in data [:styles :description-color])}]

     [:.icon
      ["&[data-icon]:before"
       {:content "attr(data-icon)"
        :font-family "FontAwesome"
        :font-size "3em"
        :color (get-in data [:styles :description-color])}]]]]))

(defn section [data]
  [:section.features-grid
   [:div.content
    (for [feature (data :features)]
     [:div.feature
       [:div.icon {:data-icon (feature :icon)}]
       [:h2 (feature :title)]
       [:p (feature :description)]])]])

(defmethod template "features-grid" [_]
  {:css styles
   :component section})
