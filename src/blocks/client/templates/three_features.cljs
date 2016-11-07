(ns blocks.client.templates.three-features
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))


(defn styles [data]
  [:.three-features
   {:background (get-in data [:styles :background])
    :box-sizing "border-box"
    :color (get-in data [:heading :color])}

   [:.content
    {:padding "6em 3em"}

    [:.columns
     {:display "flex"
      :justify-content "space-between"
      :align-items "flex-start"}

     [:.column
      {:text-align "center"
       :flex-basis (str (/ 100 (data :column)) "%")
       :min-width "14em"
       :margin-right "6em"}

      [:&:last-child
       {:margin-right 0}]

      [:img
       {:padding "0.5em 0"
        :height "3rem"}]

      [:.icon::before
       {:content (str "attr(data-icon)")
        :font-family "FontAwesome"
        :font-size "3em"
        :height "4rem"
        :line-height "4rem"}]

      [:h2
       {:font-weight "bolder"
        :font-size "1.25em"
        :padding "1em"}]

      [:p
       {:color "#fff"
        :font-size "1em"}]]]]

   (at-media {:max-width "700px"}
     [:.content
      [:.columns
       {:display "flex"
        :flex-direction "column"
        :justify-content "center"
        :align-items "center"}]])])

(defn component [data]
  [:div.three-features
   [:div.content
    (into
      [:div.columns]
      (for [column (data :columns)]
        [:div.column
         (when (column :icon)
           [:div.icon {:data-icon (column :icon)}])
         (when (column :image)
           [:img {:src (column :image)}])
         [:h2 (column :title)]
         [:p (column :description)]]))]])

(defmethod template "three-features" [_]
  {:css       styles
   :component component})
