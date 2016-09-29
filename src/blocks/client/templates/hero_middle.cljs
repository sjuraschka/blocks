(ns blocks.client.templates.hero-middle
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]
            [blocks.client.templates.partials.email-field :as email-field]))

(defn styles [data]
    [:section.hero-middle
      {:padding [["4em" 0]]
       :text-align "center"
       :display "flex"
       :flex-direction "column"
       :justify-content "center"
       :box-sizing "border-box"
       :background-color (get-in data [:background :color])
       :background-image (str "url(" (get-in data [:background :image-url]) ")")
       :background-size (get-in data [:background :size])
       :background-repeat "no-repeat"
       :background-position (get-in data [:background :position] "center bottom")}

      [:h1
       {:font-size "3.5em"
        :font-weight "bold"
        :color (get-in data [:heading :color])}]

      [:h2
       {:font-size "2em"
        :margin [["0.75em" "0.75em" "1.25em"]]
        :max-width "20em"
        :white-space "pre"
        :color (get-in data [:sub-heading :color])}]

      [:a.button
       {:margin-bottom "5em"}
       (button-mixin (get-in data [:button :colors] {}))

       [:&.download:before
        (fontawesome-mixin \uf019)
        {:margin-right "0.5em"}]]

      (email-field/styles data)])

(defn hero [data]
  [:section.hero-middle
   [:div.content
    [:h1 (get-in data [:heading :text])]
    [:h2 (get-in data [:sub-heading :text])]
    (when (data :button)
      [:a.button {:href ""} (get-in data [:button :text])])
    (when (data :form)
      [email-field/component data])]])

  (defmethod template "hero-middle" [_]
    {:css styles
     :component hero})
