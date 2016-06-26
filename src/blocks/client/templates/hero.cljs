(ns blocks.client.templates.hero
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]))

(defn styles [data]
  [:.hero
   {:background-color (get-in data [:background :color] "#000000")
    :background-image (str "url(" (get-in data [:background :image-url]) ")")
    :background-repeat "no-repeat"
    :background-size "cover"
    :background-position "center bottom"
    :color "#FFF"
    :position "relative"
    :min-height "100vh"
    :overflow "hidden"}

   [:.content
    {:height "100vh"
     :display "flex"
     :flex-direction "column"
     :justify-content "center"}

    [:.story
     {:max-width "30rem"}

     [:h1
      {:font-family "Montserrat"
       :font-size (get-in data [:heading :size] "3em")
       :text-shadow "0 0.05em 0.1em rgba(0,0,0,0.1)"
       :white-space "pre-wrap"}]

     [:h2
      {:font-size "1.5em"
       :margin "1rem 0 2rem"
       :text-shadow "0 0.05em 0.1em rgba(0,0,0,0.25)"
       :white-space "pre-wrap"}]

     [:.cta
      {:display "inline-block"
       :text-align "center"}

      [:.button
       (button-mixin (get-in data [:button :colors] {}))

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
     [:h1 (get-in data [:heading :text])]
     [:h2 (data :text)]
     (when-let [button (data :button)]
       [:div.cta
        [:a.button.try
         {:href (button :url)} (button :text) ]
        (when (button :sub-text)
          [:div.microtext
           [:a.learn {:href (button :sub-url)}
            (button :sub-text)]])])]
    [:img.image {:src (get-in data [:featured-image :url])}]]])

(defmethod template "hero" [_]
  {:css styles
   :component hero})
