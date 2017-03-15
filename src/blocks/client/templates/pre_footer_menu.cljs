(ns blocks.client.templates.pre-footer-menu
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]))

(defn styles []
  [:.pre-footer-menu
   [:div.footer-content
    {:background "#fff"
     :display "flex"
     :justify-content "space-between"
     :margin "0 2em"}

    [:h2.title
     {:text-transform "uppercase"
      :font-weight "100"
      :font-size "10px"
      :letter-spacing "2px"}]

    [:.link
     {:font-weight "100"
      :font-size "15px"
      :line-height "25px"
      :color "#BEC8CE"
      :display "block"}]

    [:div.logo-column
     {:max-width "14em"
      :padding "2em"}
     [:img
      {:height "1em"}]
     [:p
      {:font-size "0.85em"
       :padding-top "1em"
       :color ""}]]
    [:.menu-content
     [:.columns
      {:padding-top "2rem"
       :display         "flex"
       :justify-content "space-between"
       :color           "#000"}
      [:.column
       {:margin "0 1.5em"}
       [:.menu
        {:padding-top "0.5em"
         :display         "flex"
         :flex-direction  "column"
         :justify-content "space-between"}]]]]

    [:.social
     [:.icon
      [:&:after
       {:border-radius "200px"
        :color "#fff"
        :height "30px"
        :width "30px"
        :line-height "30px"
        :text-align "center"
        :display "inline-block"
        :margin-right "0.5em"}]
      [:&.facebook:after
       (fontawesome-mixin \uf09a)
       {:background "#4F7AC5"}]
      [:&.twitter:after
       (fontawesome-mixin \uf099)
       {:background "#5FCAF3"}]
      [:&.instagram:after
       (fontawesome-mixin \uf16d)
       {:background "#545859"}]
      [:&.linkedin:after
       (fontawesome-mixin \uf0e1)
       {:background "#177DA4"}]]]

    [:div.app-info
     {:padding-top "2em"}
     [:.app
      {:max-width "14rem"
       :height "3em"
       :color "#000"
       :display "flex"
       :margin "1em 0"
       :padding "1rem 0.5rem"
       :background-color "#fff"
       :font-size "0.75em"
       :text-decoration "none"
       :box-shadow "0 1px 1px 1px rgba(199, 206, 227, 0.9)"
       :border-radius "4px"
       :overflow "hidden"
       :align-items "center"
       :justify-content "center"}

      [:img
       {:height "3em"
        :margin-right "1em"}]
      [:h2
       {:text-transform "uppercase"
        :letter-spacing "0.075em"}]]]]])

(defn component [data]
  [:div.pre-footer-menu
   [:div.footer-content
    [:div.logo-column
     [:a.logo {:href (get-in data [:logo :url])}
      [:img {:src (get-in data [:logo :image-url])}]]
     [:p (get-in data [:title :description])]]
    [:div.menu-content
     (into
       [:div.columns]
       (for [column (data :columns)]
         [:div.column
          [:h2.title (column :title)]
          [:div.menu
           (for [category (column :categories)]
             [:a.link (category :name)])]]))]

    [:div.column.app-info
     [:div.social
      [:a.icon.instagram {:target "_blank"
                          :href "https://www.instagram.com/rookietraining"}]
      [:a.icon.twitter   {:target "_blank"
                          :href "https://www.twitter.com/rookiehq"}]
      [:a.icon.linkedin  {:target "_blank"
                          :href "https://www.linkedin.com/company/rookie-inc"}]
      [:a.icon.facebook  {:target "_blank"
                          :href "https://www.facebook.com/tryrookiehq"}]]]]])

;buttons for appstore or desktop app
#_[:a.app {:href "#"}
   [:img {:src (get-in data [:button :athlete :image])}]
   [:div.info
    [:h2 "Get the athlete app"]
    [:p "No download required, use the athlete app from any mobile browser"]]]
  #_  [:a.app {:href "#"}
       [:img {:src (get-in data [:button :coach :image])}]
       [:div.info
        [:h2 "Get the Coaching Platform"]
        [:p "Give feedback from your chrome browser app"]]]

(defmethod template "pre-footer-menu" [_]
  {:css       styles
   :component component})
