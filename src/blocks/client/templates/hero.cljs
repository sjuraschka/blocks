(ns blocks.client.templates.hero
  (:require [blocks.client.template :refer [template]]
            [garden.core :refer [css]]))

(def image-height "100vh")

(defn fontawesome-mixin [unicode]
  {:content (str "\"" unicode "\"")
   :font-family "FontAwesome"})

(def button-mixin
  [:&
   {:border "none"
    :border-radius "0.5rem"
    :padding "1.5rem 1.75rem"
    :font-size "1.5rem"
    :font-weight "bold"
    :cursor "pointer"
    :outline "none"
    :font-family "Lato"
    :text-decoration "none"
    :display "inline-block"
    :color "white"
    :background "#0D6C7F"
    :transition "background 0.1s ease-in-out"}

   [:&:hover
    {:background "#014F75"}]

   [:&:active
    {:background "#012156"}]])

(def styles
  [:.hero
   {:background-color "#000225"
    :background-image "url(/images/hero-bg-flip.jpg)"
    :background-repeat "no-repeat"
    :background-size "cover"
    :background-position "center bottom"
    :color "#FFF"
    :position "relative"
    :min-height "100vh"
    :overflow "hidden"
    :box-sizing "border-box"}

   [:.content
    {:height image-height
     :display "flex"
     :flex-direction "column"
     :justify-content "center"}

    [:.story
     {:max-width "30rem"}

     [:h1
      {:font-size "3em"
       :font-family "Montserrat"
       :text-shadow "0 0.05em 0.1em rgba(0,0,0,0.1)"}]

     [:h2
      {:font-size "1.5em"
       :margin "1rem 0 2rem"
       :text-shadow "0 0.05em 0.1em rgba(0,0,0,0.25)"}]

     [:.cta
      {:display "inline-block"
       :text-align "center"}

      [:.button
       button-mixin

       [:&.download:before
        (fontawesome-mixin \uf019)
        {:margin-right "0.5em"}]

       [:&.try:after
        (fontawesome-mixin \uf138)
        {:margin-left "0.5em"}]]

      [:.microtext
       {:font-size "0.8em"
        :margin-top "0.5rem"
        :color "#0D6C7F" }

       [:a
        {:color "#0D6C7F"
         :text-decoration "none"
         :transition "color 0.1s ease-in-out"}

        [:&:hover
         {:color "#014F75"}]

        [:&:active
         {:color "#012156"}]

        [:&.:after
         (fontawesome-mixin \uf101)
         {:margin-left "0.25em"}]]

       [:.linux:before :.mac:before :.ios:before
        :.android:before :.windows:before :.web:before
        {:font-family "FontAwesome"
         :-webkit-font-smoothing "antialiased"
         :margin "0 0.2em"}]

       [:.linux:before {:content "\"\\f17c\""}]
       [:.mac:before {:content "\"\\f179\""}]
       [:.ios:before {:content "\"\\f179\""}]
       [:.android:before {:content "\"\\f17b\""}]
       [:.windows:before {:content "\"\\f17a\""}]
       [:.web:before {:content "\"\\f268\""}]]]]

    [:img.image
     {:position "absolute"
      :left "33rem"
      :height "60vh"
      :top "20vh"}]]])

(defn hero [data]
  [:section.hero
   [:div.content
    [:div.story
     [:h1 (data :heading)]
     [:h2 (data :text)]
     (when-let [button (data :button)]
       [:div.cta
        [:a.button.try
         {:href (button :url)} (button :text) ]
        (when (button :sub-text)
          [:div.microtext
           [:a.learn {:href (button :sub-url)}
            (button :sub-text)]])])]
    [:img.image {:src (data :featured-image-url)}]]])

(defmethod template "hero" [_]
  {:css styles
   :component hero})
