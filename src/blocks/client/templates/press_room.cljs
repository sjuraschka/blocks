(ns blocks.client.templates.press-room
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]))

(defn styles [data]
  [:.press-room
    {:background "#fff"
     :display "flex"
     :flex-direction "column"
     :justify-content "center"
     :align-items "center"
     :border-bottom "1px solid #eee"}
   ;===========================
   ;general styles
   ;===========================

   [:h2
    {:color          "#2E3944"
     :font-size      "1.75em"
     :spacer "0.15em"
     :text-transform "uppercase"}]

   [:a
    {:font-size "1rem"
     :text-transform "lowercase"}]

   [:.border
    {:margin "1em"
     :border "1px solid #eee"
     :box-shadow "1px 2px 1px 0 rgba(200, 230, 230, 0.5)"
     :border-radius "3px"}]

   [:.button
    {:border "1px solid #ccc"
     :text-decoration "none"
     :border-radius "30px"
     :padding "0em 1em"
     :margin "1em"
     :color "#bbb"
     :font-weight "100"}
    [:&:hover
     {:background "#eee"
      :color "#3D50CF"}]
    [:&:select
     {:background "#ddd"
      :border "1px solid #3D50CF"}]]

   ;===========================
   ;nested styles
   ;===========================

   [:div.container
    {:display "flex"
     :flex-direction "column"
     :align-items "center"
     :justify-content "center"
     :padding "1em"}
    [:div.images
     {:display "flex"
      :align-items "center"
      :text-align "center"}]]

   [:.card
    {:padding "1em 4em"}
    [:h3
     {:color "#2E3944"}]
    [:p
     {:color "#BEC8CE"}]
    [:a
     {:color "#232B69"
      :text-decoration "none"}]]])


(defn component [data]
  [:div.press-room
   [:div.images.container
    [:h2 (get-in data [:logos :subtitle])
     (into
       [:div.images]
       (for [logo (data :logos)]
         [:div.image
          [:img.border {:src (logo :image-url)}]
          (into
            [:div.buttons]
            (for [button (logo :button)]
              [:a.button {:target "_blank"
                          :href (button :url)} (button :text)]))]))]]

   [:div.images.container
    [:h2 (get-in data [:product :subtitle])]
    [:img.border {:src (get-in data [:product :image])}]
    [:div.buttons
     [:a.button {:href (get-in data [:product :url])
                 :target "_blank" } (get-in data [:product :button])]]]
   [:div.contact.container
    [:h2 (get-in data [:contact :subtitle])]
    [:div.border.card
     [:h3 (get-in data [:contact :name])]
     [:p (get-in data [:contact :position])]
     [:p (get-in data [:contact :role])]
     [:a {:href (get-in data [:contact :email :url])}
      (get-in data [:contact :email :text])]
     [:p (get-in data [:contact :phone])]]]])

(defmethod template "press-room" [_]
  {:css       styles
   :component component})
