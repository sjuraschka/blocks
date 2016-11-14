(ns blocks.client.templates.quote
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.quote
   {:background "#f5f5f5"
    :box-sizing "border-box"
    :width "100vw"}

   [:.content
    {:display "flex"
     :padding "4em"
     :box-sizing "border-box"
     :flex-direction "column"
     :align-items "center"
     :text-align "center"
     :width "100vw"}

    [:.title
    {:font-size "1.2em"
     :letter-spacing "0.05em"
     :font-weight "900"
     :color "#ec881b"
     :text-transform "uppercase"}]

    [:.quote
     {:margin "0.5em 0"
      :max-width "60vw"
      :color "#9c9c9c"
      :font-size "2rem"}]

    [:.customer
     {:color "#222222"}]]])

(defn component [data]
  [:div.quote
   [:div.content
    [:div.title (data :title)]
    [:div.quote (data :quote)]
    [:div.customer (data :name)]]])


(defmethod template "quote" [_]
  {:css       styles
   :component component})
