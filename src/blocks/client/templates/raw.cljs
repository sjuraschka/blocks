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

(defn render [data]
  (cond
    (map? data)
    [:table
     [:tbody
      [:tr
       [:td "{"]]
      (for [[k v] data]
        [:tr
         [:td.key (name k)]
         [:td
          [render v]]])
      [:tr
       [:td "}"]]]]

    (vector? data)
    [:table
     [:tbody
      [:tr
       [:td "["]]
      (for [v data]
        [:tr
         [:td
          [render v]]])
      [:tr
       [:td "]"]]]]

    :else
    [:div (pr-str data)]))

(defn component [data]
  [:section.raw
   [:div.content
    [render data]]])

(defmethod template "raw" [_]
  {:css styles
   :component component})
