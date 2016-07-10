(ns blocks.client.templates.partials.email-field)

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
        {:border-radius "0 0.5em 0.5em 0"
         :border "none"
         :font-size "1em"
         :text-transform "uppercase"
         :cursor "pointer"
         :color (get-in data [:form :button :color])
         :background (get-in data [:form :button :background-color])}]]])

(defn component [data]
  [:form
    [:input {:type "email" :placeholder (get-in data [:form :placeholder])}]
    [:input {:type "submit" :value (get-in data [:form :button :text])}]])
