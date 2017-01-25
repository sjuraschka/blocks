(ns blocks.client.templates.multiple-logo-footer
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(def pad "3rem")

(defn styles [data]
  [:footer
   {:background (data :background-color)}

   [:.content
    {:display "flex"
     :flex-direction "column"
     :justify-content "center"
     :width "100%"
     :max-width "100%"
     :padding pad
     :box-sizing "border-box"}

    [:.upper
     {:display "flex"
      :justify-content "space-between"
      :align-items "center"}


     [:a.client
      {:color (data :text-color)
       :white-space "nowrap"
       :height "1em"}

      [:img
       {:height (get-in data [:client :height])
        :display "inline-block"
        :vertical-align "middle"
        :margin-right "0.5em"}]]




     [:h1.sub-text
      {:display "inline-block"
       :text-transform "uppercase"
       :color "#fff"
       :font-size "0.65em"
       :line-height "2rem"
       :margin-right "5px"
       :vertical-align "middle"}]




     [:a.logo]
     {:color (data :text-color)
      :white-space "nowrap"
      :text-decoration "none"
      :display "flex"
      :flex-direction "column"
      :justify-content "center"
      :align-items "center"}

     [:img
      {:height (data :height)
       :display "inline-block"
       :vertical-align "middle"
       :margin-right "0.5em"}]]

    [:.payment-area
     {:display "flex"
      :flex-direction "column"
      :justify-content "center"
      :align-items "center"}
     [:.payment
      {:height "1em"
       :display "inline-block"
       :vertical-align "middle"}]]

    [:.lower]


    (at-media {:max-width "600px"}

              [:.upper
               {:flex-direction "column"
                :justify-content "center"}
               [:.logo
                {:padding "1.5em 0"}]])]])

(defn view [data]
  [:footer
   [:div.content
    [:div.upper]
    [:a.client {:href  (data :website)}
     [:img.client {:src (get-in data [:client :image])}]]
    [:a.logo {:href (get-in data [:logo :url])}
     [:h1.sub-text (data :title)]
     [:img {:src (get-in data [:logo :image-url])}]]

    [:div.payment-area
     [:h1.sub-text "We Accept"]
     [:img.payment {:src "/rookie_shared/images/payment.png"}]

     [:div.lower]]
    (into
      [:nav]
      (for [link (data :menu)]
        [:a {:href (link :url)
             :class (link :style)
             :data-icon (link :icon)}
         (link :text)]))]])

(defmethod template "multiple-logo-footer" [_]
  {:css styles
   :component view})
