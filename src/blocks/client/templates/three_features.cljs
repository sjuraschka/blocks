(ns blocks.client.templates.three-features
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [button-mixin]]))


(defn styles [data]
  [:.three-features
   {:background (data :background)
    :box-sizing "border-box"
    :color (get-in data [:heading :color])}
   [:.content
    {:padding "2em"}
    [:.columns
     {:display "flex"
      :justify-content "space-around"
      :flex-wrap "wrap"
      :align-items "center"
      :font-size "1.25em"}
     [:.column
      {:text-align "center"
       :height "14em"
       :width "14em"}
      [:h2
       {:font-weight "bolder"
        :padding "1em"}]]]]])

(defn component [data]
  [:div.three-features
   [:div.content
     [:div.columns
      (for [column (data :columns)]
        [:div.column
         [:h2 (column :title)]])]]])

(defmethod template "three-features" [_]
  {:css       styles
   :component component})
