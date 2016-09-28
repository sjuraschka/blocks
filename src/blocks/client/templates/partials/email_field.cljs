(ns blocks.client.templates.partials.email-field
  (:require [blocks.client.templates.mixins :refer [button-mixin]]))

(defn styles [data]
  [:form
   {:box-shadow [[0 "1px" "2px" 0 "#ccc"]]
    :display "inline-block"
    :border-radius "0.5em"
    :overflow "hidden"}

    [:input
      {:padding "0 0.75em"
       :border "none"
       :box-sizing "border-box"
       :height "3rem"
       :vertical-align "top"
       :line-height "3rem"
       :outline "none"}

       ["&[type=email]"
        {:font-size "1.25em"
         :min-width "15em"}

      ["&::-moz-placeholder"
        {:color "#eee"}]

      ["&::-webkit-input-placeholder"
        {:color "#eee"}]]

      ["&[type=submit]"
        (button-mixin (get-in data [:form :button :colors] {}))
        [:&
          {:border-radius 0
           :font-size "1em"
           :padding "0 1em"
           :text-transform "uppercase"}]]]])

(defn component [data]
  [:form
   [:input {:type "email"
            :name "email"
            :placeholder (get-in data [:form :placeholder])
            :auto-focus (when (get-in data [:form :autofocus]) "autofocus")}]
   [:input {:type "submit"
            :value (get-in data [:form :button :text])}]])
