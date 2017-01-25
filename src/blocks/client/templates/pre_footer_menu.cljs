(ns blocks.client.templates.pre-footer-menu
  (:require [blocks.client.template :refer [template]]))

(defn styles []
  [:.pre-footer-menu
   {:background "#002184"}
   [:.content
    {:padding "3em 6em"}
    [:.columns
     {:display         "flex"
      :justify-content "space-between"
      :color           "#fff"}
     [:.column
      [:.menu
       {:display         "flex"
        :flex-direction  "column"
        :justify-content "space-between"}]]]]])

(defn component [data]
  [:div.pre-footer-menu
   [:div.content
    (into
      [:div.columns]
      (for [column (data :columns)]
        [:div.column
         [:h2 (column :title)]
         [:div.menu
          (for [category (column :categories)]
            [:a (category :name)])]]))]])

(defmethod template "pre-footer-menu" [_]
  {:css       styles
   :component component})
