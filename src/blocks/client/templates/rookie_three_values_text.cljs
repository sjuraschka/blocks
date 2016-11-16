(ns blocks.client.templates.rookie-three-values-text
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.three-values-text
   {:background   "#fff"
    :box-sizing "border-box"
    :padding-bottom "1em"
    :border-bottom "1px solid #eee"
    ;:box-shadow  "2px 5px 0px rgba(168,168,168,1)"
    }

   [:&::before
    {:content          "\"\""
     :display          "block"
     :background-image (data :background)
     :height           "25em"
     :position         "relative"
     :width            "100vw"
     :z-index          1}]

   [:.content
    {:margin-top "-22.05em"
     :z-index    100
     :padding    "1.5em 4em 0em 4em"
     :box-sizing "border-box"
     :width      "100vw"
     :display    "flex"
     :justify-content "center"}

    [:.columns
     {:display         "flex"
      :justify-content "space-between"
      :width           "100vw"}

     [:.column
      {:min-width      "30vw"
       :text-align "center"}]]

    [:h1
     {:padding-top "1em"
      :color (data :title-color)
      :text-transform "uppercase"}]


    [:p
     {:color     "#696969"
      :padding   "1em 3em"
      :font-size "0.9rem"}]

    [:a
     {:color     "blue"
      :weight    "bolder"
      :font-size "0.9rem"}]]

   (at-media {:max-width "900px"}

             [:&::before
              {:height "2px"}]

             [:.content
              {:margin-top 0}

               [:.columns
                {:flex-direction "column"}]

                  [:column
                   {:width "80vw"}]

                  [:h1
                   {:padding-top "2em"}]

                  [:p
                   {:padding       "1em 3em 2em 3em"
                    :border-bottom "1px solid #ccc"
                    :margin-bottom "2em"}]])])

(defn component [data]
  [:div.three-values-text
   [:div.content
    (into
      [:div.columns]
      (for [column (data :columns)]
        [:div.column
         [:img {:src (column :url)}]
         [:h1 (get-in column [:title :name])]
         [:p (column :subtitle)]]))]])



(defmethod template "rookie-three-values-text" [_]
  {:css       styles
   :component component})


#_ (into
  [:.columns
   {:flex-direction "column"}]

  (for [column (data :columns)]
    [:column
     {:width "80vw"}]

    [:h1
     {:padding-top "2em"}]

    [:p
     {:padding       "1em 3em 2em 3em"
      :border-bottom (column :border)
      :margin-bottom "2em"}]))