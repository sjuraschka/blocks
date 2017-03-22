(ns blocks.client.templates.center-text
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.center-text
   {:background (get-in data [:background :color])
    :display "flex"
    :flex-direction "column"
    :justify-content "center"
    :align-items "center"
    :text-align "center"
    :box-sizing "border-box"}

   [:h1]
   [:h2]
   [:h3]
   [:p
    {:color "#5f5f5f"}]])

(defn component [data]
  [:div.center-text
   [:h3 (data :city)]
   [:h2 (data :headline)]
   [:p (data :description)]])

(defmethod template "center-text" [_]
  {:css styles
   :component component})

