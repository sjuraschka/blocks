(ns blocks.client.templates.footer
  (:require [blocks.client.template :refer [template]]))

(def pad "3rem")

(defn styles [data]
  [:footer
   {:background (data :background-color)}

   [:.content
    {:display "flex"
     :width "100%"
     :max-width "100%"
     :padding pad
     :box-sizing "border-box"
     :justify-content "space-between"}

    [:a.logo
     {:color (data :text-color)
      :white-space "nowrap"}

     [:img
      {:height (data :height)
       :display "inline-block"
       :vertical-align "middle"
       :margin-right "0.5em" }]

     [:h1
      {:display "inline-block"
       :font-size "1.25em"
       :line-height "2rem"
       :vertical-align "middle" }]

     [:.version
      {:display "inline-block"
       :opacity "0.5"
       :font-size "0.75em"
       :line-height "2rem"
       :vertical-align "sub"
       :margin-left "0.5em"
       :text-transform "uppercase"
       :letter-spacing "0.1em"}]]

    [:nav
     {:text-align "right"}

     [:a
      {:color (data :text-color)
       :opacity 0.6
       :text-decoration "none"
       :margin-left "2em"
       :transition "opacity 0.1s ease-in-out"
       :line-height "2em"}

      [:&:hover
       {:opacity 1}]

      ["&[data-icon]:before"
       {:content "attr(data-icon)"
        :font-family "FontAwesome"
        :margin-right "0.25em"}]

      [:&.button
       {:display "inline-block"
        :border [["1px" "solid" (data :text-color)]]
        :border-radius "0.25em"
        :height "2em"
        :padding "0 0.5em" }]]]]])

(defn view [data]
  [:footer
   [:div.content
    [:a.logo {:href (get-in data [:logo :url])}
     [:img {:src (get-in data [:logo :image-url])}]
     [:h1 (data :title)]]
    (into
      [:nav]
      (for [link (data :menu)]
        [:a {:href (link :url)
             :class (link :style)
             :data-icon (link :icon)}
         (link :text)]))]])

(defmethod template "footer" [_]
  {:css styles
   :component view})
