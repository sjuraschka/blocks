(ns blocks.client.templates.hero
  (:require [blocks.client.template :refer [template]]))

(defmethod template "hero"
  [_]
  (fn [data]
    [:div.hero
     [:div.heading
      (data :heading)]]))
