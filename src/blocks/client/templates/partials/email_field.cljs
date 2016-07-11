(ns blocks.client.templates.partials.email-field
  (:require [blocks.client.templates.mixins :refer [button-mixin]]))

(defn styles [data]
  [:form
    [:input
      {:padding "0 0.75em"
       :border "none"
       :box-sizing "border-box"
       :height "3rem"
       :vertical-align "top"
       :line-height "3rem"}

       ["&[type=email]"
        {:border-radius "0.5em 0 0 0.5em"
        :font-size "1.25em"
         :min-width "15em"}

      ["&::-moz-placeholder"
        {:color "#ccc"}]

      ["&::-webkit-input-placeholder"
        {:color "#ccc"}]]

      ["&[type=submit]"
        (button-mixin (get-in data [:form :button :colors] {}))
        [:&
          {:border-radius "0 0.5em 0.5em 0"
           :font-size "1em"
           :padding "0 1em"
           :text-transform "uppercase"}]]]])

(defn component [data]
  [:form
   [:input {:type "email"
            :name "email"
            :placeholder (get-in data [:form :placeholder])
            :autofocus (when (get-in data [:form :autofocus]) "autofocus")}]
   [:input {:type "submit"
            :value (get-in data [:form :button :text])}]])
