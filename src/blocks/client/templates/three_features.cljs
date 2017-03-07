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

    [:h1
     {:text-transform (get-in data [:title :transform])
      :text-align "center"
      :padding "2em"
      :color  (get-in data [:title])
      :font-size      "1.75em"
      :letter-spacing (get-in data [:title :spacing])}]


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

      [:.circle
       {:background (get-in data [:heading :color])
        :display "inline-block"
        :text-align "center"
        :vertical-align "middle"
        :line-height "3em"
        :width "3em"
        :height "3em"
        :border-radius "100px"}

       [:p
        {:color (get-in data [:styles :background])
         :font-weight "400"
         :font-size "1rem"}]]

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
       {:color (get-in data [:heading :color])
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
    [:h1 (get-in data [:title :copy])]
    (into
      [:div.columns]
      (for [column (data :columns)]
        [:div.column
         (when (column :number)
           [:div.circle
            [:p (column :number)]])
         (when (column :icon)
           [:div.icon {:data-icon (column :icon)}])
         (when (column :image)
           [:img {:src (column :image)}])

         [:h2 (column :title)]
         [:p (column :description)]]))]])

(defmethod template "three-features" [_]
  {:css       styles
   :component component})
