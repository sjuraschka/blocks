(ns blocks.client.templates.rookie-three-values-text
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.three-values-text
   {:background    "#fff"
    :box-sizing    "border-box"
    :border-bottom "1px solid #eee"}

   [:&::before
    {:content          "\"\""
     :display          "block"
     :background-image (data :background)
     :height           "29em"
     :position         "relative"
     :width            "100vw"
     :z-index          1}]

   [:.content
    {:margin-top     "-27em"
     :z-index        100
     :padding        "1.5em 4em 0em 4em"
     :box-sizing     "border-box"
     :width          "100vw"
     :display        "flex"
     :flex-direction "column"
     :align-items    "center"}

    [:.title
     {:font-size      "1.75em"
      :box-sizing     "border-box"
      :color          "#fff"
      :padding-bottom "1.5em"}]

    [:.columns
     {:display         "flex"
      :text-align      "center"
      :justify-content "space-between"
      :width           "100vw"}

     [:.column
      {:width          "30vw"
       :box-sizing     "border-box"
       :text-align     "center"
       :display        "flex"
       :flex-direction "column"
       :align-items    "center"
       :padding-bottom "1em"}

      [:h1.section-title
       {:padding-top    "1em"
        :color          (data :title-color)
        :text-transform "uppercase"}]

      [:p
       {:color      "#696969"
        :box-sizing "border-box"
        :padding    "1em 2em"
        :width      "28vw"
        :font-size  "0.9rem"}]

      [:a
       {:color     "blue"
        :weight    "bolder"
        :font-size "0.9rem"}]]]]

   (at-media {:max-width "900px"}

             [:&::before
              {:height "2px"}]

             [:.content
              {:margin-top 0}

              [:.title
               {:color          (data :title-color)
                :padding-bottom 0
                :text-align     "center"
                :font-size      "1.3em"
                :font-weight    "bold"}]

              [:.columns
               {:flex-direction "column"
                :width          "100vw"}

               [:.column
                {:width           "100vw"
                 :justify-content "center"
                 :border-bottom   "1px solid #eee"
                 :padding-top     "2em"}

                [:p
                 {:width         "100%"
                  :padding       "1em 2em"
                  :margin-bottom "1em"}]]]])])

(defn component [data]
  [:div.three-values-text
   [:div.content
    [:h1.title (data :header)]
    (into
      [:div.columns]
      (for [column (data :columns)]
        [:div.column
         [:img {:src (column :url)}]
         [:h1.section-title (get-in column [:title :name])]
         [:p (column :subtitle)]]))]])

(defmethod template "rookie-three-values-text" [_]
  {:css       styles
   :component component})


