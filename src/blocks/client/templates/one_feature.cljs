(ns blocks.client.templates.one-feature
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.one-feature
   {:background (get-in data [:background :color])
    :display "flex"
    :flex-direction "column"
    :justify-content "center"
    :align-items "center"
    :text-align "center"
    :box-sizing "border-box"
    :padding-top (get-in data [:page-padding :top])}

   [:.content
    {:padding "3em"}
    [:h1
     {:color (get-in data [:heading :color])
      :font-size "1.75em"
      :font-weight "bolder"
      :text-transform (get-in data [:heading :transform])}]
    [:p
     {:color (get-in data [:description :color])
      :font-size "1em"
      :padding "0.5em"}]]])

(defn component [data]
  [:div.one-feature
   [:div.content
    [:h1 (get-in data [:heading :text])]
    [:p (get-in data [:description :text])]]])

(defmethod template "one-feature" [_]
  {:css styles
   :component component})