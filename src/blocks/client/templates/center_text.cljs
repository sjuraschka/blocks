(ns blocks.client.templates.center-text
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.center-text
   {:padding "3em"
    :background (get-in data [:styles :background :color])
    :text-align "center"
    :display "flex"
    :justify-content "center"
    :box-sizing "border-box"}

   [:.text
    {:text-align "center"
     :display "flex"
     :flex-direction "column"
     :justify-content "center"
     :align-items "center"
     :max-width "21em"}

    [:.headline
     {:color (get-in data [:styles :heading :color])
      :font-size (get-in data [:styles :heading :size])
      :margin "1em"}]

    [:.label
     {:color (get-in data [:styles :label :color])
      :text-transform (get-in data [:styles :label :transform])
      :font-size (get-in data [:styles :label :size])}]

    [:p
     {:color (get-in data [:styles :subtitle :color])
      :font-size (get-in data [:styles :subtitle :size])}]]])

(defn component [data]
  [:div.center-text
   [:div.text
    [:h3.label (data :city)]
    [:h2.headline (data :headline)]
    [:p.description (data :description)]]])

(defmethod template "center-text" [_]
  {:css styles
   :component component})

