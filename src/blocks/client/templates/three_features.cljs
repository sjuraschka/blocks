(ns blocks.client.templates.three-features
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [button-mixin]]))


(defn styles [data]
  [:.three-features
   {:background "#eee"
    :box-sizing "border-box"
    :border-bottom "1px solid #d6d6d6"}
   [:.content
    {:padding "2em"}
    [:.columns
     {:display "flex"
      :justify-content "space-around"
      :flex-wrap "wrap"
      :align-items "center"
      :font-size "1.25em"}
     [:.column
      {:background "#fff"
       :box-shadow     [[0 "1px" "2px" 0 "#ccc"]]
       :border-radius "0.25em"
       :text-align "center"
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
