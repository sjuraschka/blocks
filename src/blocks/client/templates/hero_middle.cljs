(ns blocks.client.templates.hero-middle
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]
            [blocks.client.templates.partials.email-field :as email-field]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:section.hero-middle
   {:padding [[(get data :padding "4em") 0]]
    :text-align "center"
    :display "flex"
    :flex-direction "column"
    :justify-content "center"
    :align-items "center"
    :box-sizing "border-box"
    :height (data :height)
    :background-color (get-in data [:background :color])
    :background-image (str "url(" (get-in data [:background :image-url]) ")")
    :background-size (get-in data [:background :size])
    :background-repeat "no-repeat"
    :background-position (get-in data [:background :position] "right bottom")}

   [:.content
    {:display "flex"
     :flex-direction "column"
     :justify-content "center"
     :align-items "center"}

    [:h1
     {:font-size (get-in data [:heading :size])
      :font-weight (get-in data [:heading :weight])
      :color (get-in data [:heading :color])
      :white-space "pre"
      :text-transform (get-in data [:heading :title-type])}]

    [:p
     {:font-size "1.4em"
      :margin [["0.75em" "0.75em" "1.25em"]]
      :width (get-in data [:sub-heading :width])
      :text-align "center"
      :white-space "pre"
      :color (get-in data [:sub-heading :color])}

     [:em
      {:font-weight "bold"}]]

    [:a.button
     {:margin-bottom "5em"}
     (button-mixin (get-in data [:button :colors] {}))

     [:&.download:before
      (fontawesome-mixin \uf019)
      {:margin-right "0.5em"}]]

    (email-field/styles (data :form))]

   (at-media {:max-width "800px"}
             [:&
              {:background-image (str "url(" (get-in data [:background :mobile-url]) ")")
               :width "100vw"}

              [:.content
               {:width "100vw"
                :box-sizing "border-box"
                :display "flex"
                :flex-direction "column"
                :justify-content "center"
                :align-items "center"}

                [:h1
                 {:font-size "1.75em"
                  :white-space "normal"
                  :width "20rem"}]

                ["> p"
                 {:font-size "1.25em"
                  :color (get-in data [:sub-heading :mobile-color])
                  :width "17rem"
                  :white-space "normal"}]]])])


(defn hero [data]
  [:section.hero-middle
   [:div.content
    [:h1 (get-in data [:heading :text])]
    [:p {:dangerouslySetInnerHTML
          {:__html (get-in data [:sub-heading :text])}}]
    (when (data :button)
      [:a.button {:href ""} (get-in data [:button :text])])
    (when (data :form)
      [email-field/component (data :form)])]])

(defmethod template "hero-middle" [_]
  {:css styles
   :component hero})
