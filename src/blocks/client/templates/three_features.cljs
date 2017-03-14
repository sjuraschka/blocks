(ns blocks.client.templates.three-features
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.three-features
   {:background (get-in data [:styles :background])
    :box-sizing "border-box"
    :color (get-in data [:heading :color])}

   [:.content
    {:padding "4em 3em"}

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
      {:display "flex"
       :flex-direction "column"
       :align-items "center"
       :text-align "center"
       :flex-basis (str (/ 100 (data :column)) "%")
       :min-width "14em"
       :padding "2em"}

      [:&:last-child
       {:margin-right 0}]

      [:img
       {:padding "0.5em 0"
        :width (get-in data [:image :width])}]

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
       {:font-size (get-in data [:column-title :size])
        :text-transform (get-in data [:column-title :transform])
        :padding "1em 0"}]

      [:p
       {:color (get-in data [:heading :color])
        :font-size "1em"}]]]]

   (at-media {:max-width "700px"}
     [:.content
      {:height "220vh"}
      [:.columns
       {:display "flex"
        :flex-direction "column"
        :align-items "center"
        :justify-content "center"}

       [:column
        {:padding 0
         :text-align "center"
         :max-width "100%"
         :flex-basis 0
         :margin-right 0}]]])])

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
