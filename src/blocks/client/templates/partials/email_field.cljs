(ns blocks.client.templates.partials.email-field
  (:require [blocks.client.templates.mixins :refer [button-mixin fontawesome-mixin]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:form
   [:p.after :p.before
    {:font-size "0.8em"
     :line-height "1.5em"
     :white-space "pre"
     :margin "0.5em"}]

   [:p.before
    {:color (get-in data [:before :color])}]

   [:p.after
    {:color (get-in data [:after :color])}

    (when (get-in data [:after :icon])
      [:&::after
       (fontawesome-mixin (get-in data [:after :icon]))
       {:display "block"}])]

   [:fieldset
    {:box-shadow [[0 "2px" "5px" 0 "rgba(167,167,167,0.45)"]]
     :display "inline-block"
     :border-radius "5px"
     :overflow "hidden"}

    [:input
     {:padding "0 0.75em"
      :border "none"
      :box-sizing "border-box"
      :background (get-in data [:form :background])
      :height "3rem"
      :vertical-align "top"
      :line-height "3rem"
      :outline "none"}

     ["&[type=email]"
      {:font-size "1.25em"
       :min-width "13em"}

      ["&::-moz-placeholder"
       {:color "#ddd"}]

      ["&::-webkit-input-placeholder"
       {:color "#ddd"}]]

     ["&[type=submit]"
      (button-mixin (get-in data [:button :colors] {}))
      [:&
       {:border-radius 0
        :font-size "1em"
        :padding "0 1em"
        :letter-spacing "0.05em"
        :text-transform "uppercase"}]]]]

   (at-media {:max-width "450px"}
             [:fieldset
              {:display "flex"
               :flex-direction "column"}
              {}
             [:input
              {:width "100%"}]

              ["&[type=email]"
               {:font-size "1em"
                :min-width "9em"}]])])

(defn component [data]
  [:form
   (when (get-in data [:before :text])
     [:p.before (get-in data [:before :text])])
   [:fieldset
    [:input {:type "email"
             :name "email"
             :placeholder (get-in data [:placeholder])
             :auto-focus (when (get-in data [:autofocus]) "autofocus")}]
    [:input {:type "submit"
             :value (get-in data [:button :text])}]]
   (when (get-in data [:after :text])
     [:p.after (get-in data [:after :text])])])
