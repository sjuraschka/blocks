(ns blocks.client.templates.raw
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.raw
   {:padding "3em"}

   [:table
    [:td
     {:padding "0.5em"}]
    [:.key
     {:font-weight "bold"
      :text-align "right"}]]])

(defn table [data]
  [:table
   [:tbody
    (for [[k v] data]
      [:tr
       [:td.key (name k)]
       [:td
        (if (map? v)
          [table v]
          (pr-str v))]])]])

(defn component [data]
  [:section.raw
   [:div.content
    [table data]]])

(defmethod template "raw" [_]
  {:css styles
   :component component})
