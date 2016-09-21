(ns blocks.client.templates.one-feature
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.one-feature
   {:background (data :background)
    :display "flex"
    :flex-direction "column"
    :justify-content "center"
    :align-items "center"
    :text-align "center"
    :box-sizing "border-box"}

   [:.content
    {:padding "3em"}
    [:h1
     {:color (get-in data [:heading :color])
      :font-size "2.5em"
      :font-weight "bolder"}]
    [:h2
     {:color (get-in data [:description :color])
      :padding "1em"}]]])

(defn component [data]
  [:div.one-feature
   [:div.content
   [:h1 (get-in data [:heading :text])]
   [:h2 (get-in data [:description :text])]]])

(defmethod template "one-feature" [_]
  {:css styles
   :component component})