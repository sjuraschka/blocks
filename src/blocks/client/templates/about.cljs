(ns blocks.client.templates.about
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.about
   {:background (get-in data [:background :color])
    :display "flex"
    :border-bottom "1px solid #eee"
    :flex-direction "column"
    :justify-content "center"
    :align-items "center"
    :text-align "center"
    :box-sizing "border-box"}

   [:.text-content
    {:padding "1em"}
    [:p
     {:color (get-in data [:description :color])
      :font-size "1em"
      :text-align "left"
      :padding "0.5em"
      :max-width "40em"}]]

   [:div.containers
    {:display "flex"
     :margin "0.5em"}

    [:.container
     {:position "relative"
      :border "1px solid #ddd"
      :margin "0.5em"
      :border-radius "3px"
      :padding "1em"
      :max-width "12em"
      :height "12em"
      :display "flex"
      :flex-direction "column"
      :align-items "center"
      :justify-content "center"}

     [:h2
      {:position "absolute"
       :top "2em"
       :color (get-in data [:styles :color])}]
     [:p (get-in data [:styles :color])]

     [:a
      {:position "absolute"
       :bottom "2em"
       :text-decoration "none"
       :color (get-in data [:styles :link-color])}]]]])

(defn component [data]
  [:div.about
   [:div.text-content
    [:div
     [:h2 ""]
     (into
       (for [paragraph (data :description)]
         [:p (paragraph :item)]))]]

   [:div.containers
    (into
     ( for [container (data :containers)]
      [:div.container
       [:h2 (container :heading)]
       [:p (container :text)]
       [:a {:href (container :url)}
        (container :button-text)]]))]])

(defmethod template "about" [_]
  {:css styles
   :component component})
