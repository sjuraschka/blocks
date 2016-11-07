(ns blocks.client.templates.three-features
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))


(defn styles [data]
  [:.three-features
   {:background (get-in data [:styles :background])
    :box-sizing "border-box"
    :color (get-in data [:heading :color])}
   [:.content
    {:padding "2em"}
    [:.columns
     {:display "flex"
      :justify-content "space-between"
      :align-items "center"}
     [:.column
      {:text-align "center"
       :flex-basis (str (/ 100 (data :column)) "%")
       :min-width "14em"
       :height "14em"
       :padding "0 1em"
       :box-sizing "border-box"}
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
     [:div.columns
      (for [column (data :columns)]
        [:div.column
         [:h2 (column :title)]
         [:p (column :description)]])]]])

(defmethod template "three-features" [_]
  {:css       styles
   :component component})
