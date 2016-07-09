(ns blocks.client.templates.hero-middle
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]))

(defn styles [data]
    [:section.hero-middle
      {:padding [["8em" 0]]
       :text-align "center"
       :display "flex"
       :flex-direction "column"
       :justify-content "center"
       :box-sizing "border-box"
       :background-color (get-in data [:background :color])
       :background-image (str "url(" (get-in data [:background :image-url]) ")")
       :background-size (get-in data [:background :size])
       :background-repeat "no-repeat"
       :background-position "center bottom"}

      [:h1
       {:font-size "3.5em"
        :font-weight "bold"
        :color (get-in data [:heading :color])}]

      [:h2
       {:font-size "2em"
        :margin "0.75em"
        :max-width "20em"
        :color (get-in data [:sub-heading :color])}]

      [:a.button
       {:margin-bottom "5em"}
       (button-mixin (get-in data [:button :colors] {}))
       [:&.download:before
        (fontawesome-mixin \uf019)
        {:margin-right "0.5em"}]]

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
             :cursor "pointer"}]]]])

(defn hero [data]
  [:section.hero-middle
   [:div.content
    [:h1 (get-in data [:heading :text])]
    [:h2 (get-in data [:sub-heading :text])]
    (when (data :button)
      [:a.button {:href ""} (get-in data [:button :text])])
    (when (data :form)
      [:form
        [:input {:type "email" :placeholder (get-in data [:form :placeholder])}]
        [:input {:type "submit" :value (get-in data [:form :button :text])}]])]])

  (defmethod template "hero-middle" [_]
    {:css styles
     :component hero})
