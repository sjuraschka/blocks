(ns blocks.client.templates.cta
  (:require [blocks.client.template :refer [template]]))

  (def pad "3em")

  (defn styles [data]
    [:.cta
      {:padding pad
       :background (get-in data [:background :color])
       :text-align "center"}

    [:.content

      [:h1
        {:color (get-in data [:heading :color])
         :font-size "2.2em"
         :margin-bottom "0.5em"}]

      [:p
        {:color (get-in data [:description :color])}
        {:margin-bottom "2em"}]

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
             :color (get-in data [:form :button :colors])
             :background (get-in data [:form :button :background-color])}]]]]])

  (defn component [data]
    [:section.cta
      [:div.content
        [:div.text
          [:h1 (get-in data [:heading :text])]
          [:p  (get-in data [:description :text])]]
          [:form
            [:input {:type "email" :placeholder (get-in data [:form :placeholder])}]
            [:input {:type "submit" :value (get-in data [:form :button :text])}]]]])

  (defmethod template "cta" [_]
    {:css styles
     :component component})
