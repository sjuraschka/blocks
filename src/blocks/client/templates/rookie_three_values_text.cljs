(ns blocks.client.templates.rookie-three-values-text
  (:require [blocks.client.template :refer [template]]))

(defn styles []
  [:.three-values-text
   {:background    "#fff"
    :padding-bottom "1em"
    :border-bottom "1px solid #eee"
    :border-top "10px solid #red"}

   [:&::before
    {:content          "\"\""
     :display          "block"
     :background-image "radial-gradient(50% -16%, #4B4EC2 3%, #232B69 100%)"
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
     :display    "flex"}

    [:.columns
     {:display         "flex"
      :justify-content "space-between"
      :width           "100vw"}

     [:.column
      {:width      "30vw"
       :text-align "center"}]]

    [:h1
     {:padding-top "1em"
      :color          "#232B69"
      :text-transform "uppercase"}]

    [:p
     {:color     "#696969"
      :padding   "1em 3em"
      :font-size "0.9rem"}]

    [:a
     {:color     "blue"
      :weight    "bolder"
      :font-size "0.9rem"}]]])

(defn component [data]
  [:div.three-values-text
   [:div.content
    (into
      [:div.columns]
      (for [column (data :columns)]
        [:div.column
         [:img {:src (column :url)}]
         [:h1 (column :title)]
         [:p (column :subtitle)]
         [:a "Learn More"]]))]])



(defmethod template "rookie-three-values-text" [_]
  {:css       styles
   :component component})
