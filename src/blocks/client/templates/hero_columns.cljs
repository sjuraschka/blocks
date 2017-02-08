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
    :background-color (get-in data [:background :color])
    :background-image "linear-gradient(45deg, #593eef 28%,#593eef 28%,#232b69 100%)"
    :background-size (get-in data [:background :size])
    :background-repeat "no-repeat"
    :background-position (get-in data [:background :position] "right bottom")}

   [:.page-content
    {:width "80%"
     :display "flex"
     :justify-content "space-between"}



    [:div.right
     {}

     [:img
      {:height "660px"
       :position "relative"
       :z-index 100}]

     [:video
      {:position "absolute"
       :left "1.25em"
       :top "-2em"
       :border-radius "2em"}]]



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
      {:font-size "1.4em"
       :margin [["0.75em" 0]]
       :width (get-in data [:sub-heading :width])
       :text-align "left"
       :white-space "pre"
       :font-weight (get-in data [:sub-heading :weight])
       :color (get-in data [:sub-heading :color])}

      [:em
       {:font-weight "bold"}]]

     [:a.button
      {:margin-bottom "5em"}
      (button-mixin (get-in data [:button :colors] {}))

      [:&.download:before
       (fontawesome-mixin \uf019)
       {:margin-right "0.5em"}]]

     (email-field/styles (data :form))]]


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
                 :width "20rem"}]

               ["> p"
                {:font-size "1.15em"
                 :color (get-in data [:sub-heading :mobile-color])
                 :width "17rem"
                 :white-space "normal"}]]])])


(defn hero [data]
  [:section.hero-columns
   [:div.page-content
    [:div.content.left
     [:h1 (get-in data [:heading :text])]
     [:p {:dangerouslySetInnerHTML
           {:__html (get-in data [:sub-heading :text])}}]
     (when (data :button)
       [:a.button {:href ""} (get-in data [:button :text])])
     (when (data :form)
       [email-field/component (data :form)])]
    [:div.content.right
     [:img.image {:src (get-in data [:featured-image :url])}]
     [:video {:controls true
              :auto-play true
              :preload "auto"
              :fullscreen false
              :muted true
              :loop true
              :src "/rookie/images/test-video-small.m4v"
              :width "290"
              :height "690"}]]]])

(defmethod template "hero-columns" [_]
  {:css styles
   :component hero})
