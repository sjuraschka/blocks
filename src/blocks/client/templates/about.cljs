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
    {:padding "3em"}
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
     {:border "1px solid #ddd"
      :margin "0.5em"
      :border-radius "3px"
      :padding "1em"
      :max-width "12em"
      :height "12em"
      :display "flex"
      :flex-direction "column"
      ;:justify-content "space-between"
      :align-items "center"}
     [:img
      {:height "4em"
       :margin-bottom "1em"}]]

    [:a
     {:text-decoration "none"}]]])


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
       [:img.icon {:src (container :image)}]
       [:h2 (container :heading)]
       [:p (container :text)]
       [:a {:href (container :url)}
        (container :button-text)]]))]])


(defmethod template "about" [_]
  {:css styles
   :component component})
