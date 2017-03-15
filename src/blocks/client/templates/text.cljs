(ns blocks.client.templates.text
  (:require [blocks.client.template :refer [template]]
    [garden.stylesheet :refer [at-media]]
    [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]))

(defn styles [data]
  [:.one-column
   {:padding "3em 4em"
    :background    "#fff"
    :box-sizing    "border-box"
    :border-bottom "1px solid #eee"
    :font-weight "100"}

   [:div.date
    {:color "#bbb"
     :margin-bottom "1em"}]
   [:.section
    {:margin-bottom "1em"}
    [:h2.title
     [:span
      {:padding-right "0.5em"}]]
    [:p]
    [:ul
     {:list-style-type "circle"
      :padding-left "2em"}
     [:li]]]])

(defn view [data]
  [:div.one-column
   [:div.date (get-in data [:revision :date])]
   (into
     [:div.text-sections]
     (for [section (data :sections)]
       [:div.section
        [:h2.title
         [:span.number (section :section)]
         (section :title)]
        [:p (section :text)]
        (for [list (section :list)]
           [:ul
            [:li (list :li)]])
        [:p (get-in section [:secondary-text :text])]
        (for [list-two (get-in section [:secondary-text :secondary-list])]
          [:ul
           [:li (list-two :bullet)]])]))])

(defmethod template "text" [_]
  {:css styles
   :component view})
