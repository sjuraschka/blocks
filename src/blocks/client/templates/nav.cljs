(ns blocks.client.templates.nav
  (:require [blocks.client.template :refer [template]]
            [garden.core :refer [css]]))

(def pad "3rem")

(def styles
  [:.header
   {:position "absolute"
    :z-index 100
    :width "100%"
    :margin [[pad 0]]}

   [:a.logo
    {:display "inline-block"
     :color "#FFF"}

    [:&:before
     {:content "\"\""
      :width "2rem"
      :height "2rem"
      :display "inline-block"
      :background "url(/images/braid-logo-bw.svg) no-repeat"
      :background-size "contain"
      :vertical-align "middle"
      :margin-right "0.5em" }]

    [:h1
     {:display "inline-block"
      :font-size "1.25em"
      :line-height "2rem"
      :vertical-align "middle"}]

    [:.version
     {:display "inline-block"
      :opacity "0.5"
      :font-size "0.75em"
      :line-height "2rem"
      :vertical-align "sub"
      :margin-left "0.5em"
      :text-transform "uppercase"
      :letter-spacing "0.1em"}]]

   [:.menu
    {:display "inline-block"
     :position "absolute"
     :right 0}

    [:a
     {:color "#FFF"
      :opacity 0.6
      :text-decoration "none"
      :margin-left "2em"
      :transition "opacity 0.1s ease-in-out"
      :line-height "2em"}

     [:&:hover
      {:opacity 1}]

     [:&.button
      {:display "inline-block"
       :border "1px solid #FFF"
       :border-radius "0.25em"
       :height "2em"
       :padding "0 0.5em" }]]]])

(defn nav [data]
  [:section.header
   [:div.content
    [:a.logo {:href "/"}
     [:h1 "Braid"]
     [:span.version "beta"]]
    [:div.menu
     (for [link (data :menu)]
       [:a {:href (link :url)
            :class (link :style)}
        (link :text)])]]])

(defmethod template "nav" [_]
  {:css styles
   :component nav})
