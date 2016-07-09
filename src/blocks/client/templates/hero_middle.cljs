(ns blocks.client.templates.hero-middle
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]))
(defn styles [data]
    [:section.hero-middle
      {:min-height "75vh"
       :text-align "center"
       :display "flex"
       :flex-direction "column"
       :justify-content "center"
       :background-color (get-in data [:background :color])
       :background-image (str "url(" (get-in data [:background :image-url]) ")")
       :background-size "40em"
       :background-repeat "no-repeat"
       :background-position "center bottom"}

      [:h1 {
        :font-size "3.5em"
        :font-weight "bold"
        :color (get-in data [:styles :main-heading-color])
        }]

      [:h2 {
        :font-size "2em"
        :margin "0.75em"
        :max-width "20em"
        :color (get-in data [:styles :sub-text-color])}]

        [:a.button
         {:margin-bottom "5em"}
         (button-mixin (get-in data [:button :colors] {}))
         [:&.download:before
          (fontawesome-mixin \uf019)
          {:margin-right "0.5em"}]] ])

(defn hero [data]
  [:section.hero-middle
   [:div.content
    [:h1 (get-in data [:heading :text])]
    [:h2 (get-in data [:sub-heading :text])]
    [:a.button {:href ""} (get-in data [:button :text])]
      ]])





  (defmethod template "hero-middle" [_]
    {:css styles
     :component hero})
