(ns blocks.client.templates.features-grid
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  (let [padding "1.5em"]

    [:.features-grid
     {:padding-top "2rem"
      :background (get-in data [:styles :background-color])}

     [:.content
      {:display "flex"
       :flex-direction "column"
       :justify-content "center"
       :align-items "center"
       :padding padding}

      [:h1
       {:text-align "center"
        :-webkit-font-smoothing "antialiased"
        :font-size "1.75em"
        :color (get-in data [:styles :main-color])}]

      [:h2.subtitle
       {:font-size "1.25em"
        :padding-top "1rem"
        :color (get-in data [:styles :description-color])
        :padding-bottom "2em"}]

      [:.grid
       {:display "flex"
        :flex-wrap "wrap"
        :justify-content "center"}

       [:.feature
        {:padding padding
         :box-sizing "border-box"
         :flex-basis (str (/ 100 (data :columns)) "%")
         :min-width "20em"}

        [:h2
         {:font-weight "bold"
          :color (get-in data [:styles :heading-color])
          :margin-bottom "0.25em"}]

        [:p
         {:color (get-in data [:styles :description-color])}]

        [".icon[data-icon]::before"
         {:content "attr(data-icon)"
          :font-family "FontAwesome"
          :-webkit-font-smoothing "antialiased"
          :display "block"
          :margin-bottom "0.5rem"
          :font-size "3em"
          :color (get-in data [:styles :icon-color])}]]]]]))

(defn section [data]
  [:section.features-grid
   [:div.content
    [:h1 (data :title)]
    [:h2.subtitle (data :subtitle)]

    (into
      [:div.grid]
      (for [feature (data :features)]
        [:div.feature
         [:div.icon {:data-icon (feature :icon)}]
         [:h2 (feature :title)]
         [:p (feature :description)]]))]])

(defmethod template "features-grid" [_]
  {:css styles
   :component section})
