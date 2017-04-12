(ns blocks.client.templates.hero-columns
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]
            [blocks.client.templates.partials.email-field :as email-field]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:section.hero-columns
   {:padding [[(get data :padding "1em") 0]]
    :text-align "center"
    :display "flex"
    :flex-direction "column"
    :justify-content "center"
    :align-items "center"
    :box-sizing "border-box"
    :height (data :height)
    :background-image (str "url(" (get-in data [:background :image-url]) ")")
    :background-size "cover"
    :background-repeat "no-repeat"
    :background-position (get-in data [:background :position] "right bottom")}

   [:.page-content
    {:width "80%"
     :display "flex"
     :justify-content "space-between"}

    [:.left
     {:display "flex"
      :flex-direction "column"
      :text-align (get-in data [:heading :alignment])
      :justify-content "center"
      :align-items "flex-start"
      :-webkit-font-smoothing "antialiased"}

     [:h1
      {:font-size (get-in data [:heading :size])
       :font-weight (get-in data [:heading :weight])
       :color (get-in data [:heading :color])
       :white-space "pre"
       :line-height "0.9em"
       :letter-spacing "0.1em"
       :text-transform (get-in data [:heading :title-type])}]

     [:p
      {:font-size "1em"
       :margin [["0.75em" 0]]
       :width (get-in data [:sub-heading :width])
       :text-align "left"
       :white-space "pre"
       :font-weight (get-in data [:sub-heading :weight])
       :color (get-in data [:sub-heading :color])}
      [:em
       {:font-weight "bold"}]]

     [:.cta
      {:display "flex"}

      [:a.button
       {:text-transform "uppercase"
        :font-weight "600"
        :font-family "-apple-system, BlinkMacSystemFont, Roboto,\"Droid Sans\",\"Helvetica Neue\",Helvetica, Arial, sans-serif"}]

      [:a.button.signup
       (button-mixin (get-in data [:button :signup :colors]))
       {:margin-left "1em"}]

      [:a.button.demo
       (button-mixin (get-in data [:button :demo :colors]))

       [:&:before
        (fontawesome-mixin \uf01d)
        {:margin-right "0.5em"}]]

      (email-field/styles (data :form))]]

    [:div.right]]

   (at-media {:max-width "700px"}
             [:&
              {:background-image (str "url(" (get-in data [:background :mobile-url]) ")")
               :width "100vw"}

              [:.content.left
               {:width "100vw"
                :box-sizing "border-box"
                :display "flex"
                :flex-direction "column"
                :justify-content "center"
                :align-items "center"}

               [:h1
                {:font-size "1.65em"
                 :white-space "normal"
                 :width "20rem"
                 :text-align "center"}]

               ["> p"
                {:font-size "1.15em"
                 :color (get-in data [:sub-heading :mobile-color])
                 :width "17rem"
                 :white-space "normal"
                 :text-align "center"}]

               [:.cta
                {:flex-direction "column"}
                [:.button
                 {:margin-top "1em"
                  :width "15em"
                  :text-align "center"}]
                [:a.button.signup
                 {:margin-left 0}]]]])])


(defn hero [data]
  [:section.hero-columns
   [:div.page-content
    [:div.content.left
     [:h1 (get-in data [:heading :text])]
     [:p {:dangerouslySetInnerHTML
          {:__html (get-in data [:sub-heading :text])}}]
     [:div.cta
      [:a.button.demo {:href (get-in data [:button :demo :url])}
       (get-in data [:button :demo :text])]
      [:a.button.signup {:href (get-in data [:button :signup :url])}
       (get-in data [:button :signup :text])]]]
    [:div.content.right]]])


(defmethod template "hero-columns" [_]
  {:css styles
   :component hero})
