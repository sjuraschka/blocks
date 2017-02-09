(ns blocks.client.templates.hero-testimonial
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]
            [blocks.client.templates.partials.email-field :as email-field]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:section.hero-testimonial
   {:padding [[(get data :padding "1em") 0]]
    :box-sizing       "border-box"
    :position         "relative"
    :display          "flex"
    :justify-content  "flex-end"
    :align-items "center"
    :height (data :height)
    :width            "100vw"
    :background-color (get-in data [:background :color])
    :background-image "linear-gradient(82deg, #A4BAC0 16%, #95C5D2 92%)"
    :background-size (get-in data [:background :size])
    :background-repeat "no-repeat"
    :background-position (get-in data [:background :position] "right bottom")}

   [:.page-content
    {:width "100vw"}

    [:div.right
     {:width "100%"
      :position "absolute"
      :right 0
      :bottom 0
      :top 0}

     [:div.image
      {:position "absolute"
       :right 0
       :top 0
       :bottom 0
       :background-image  (str "url(" (get-in data [:featured-image :url]) ")")
       :background-repeat "no-repeat"
       :height            "100vh"
       :width "960px"}

      [:.caption
       {:position "relative"
        :z-index 50
        :top "15em"
        :left "25em"
        :font-size "9px"
        :white-space "pre"
        :opacity "0.5"}


       [:div.coach-name
        {}]

       [:a.coach-link
        {:text-decoration "none"
         :text-transform "uppercase"
         :color "#7A51B1"}
        [:&::after
         (fontawesome-mixin \uf138)
         {:padding-left "0.5em"}]
        [:&:hover
         {:font-weight "600"}]]]]]

    [:.left
     {:display "flex"
      :position "relative"
      :padding "0 3em"
      :z-index "50"
      :flex-direction "column"
      :text-align (get-in data [:heading :alignment])
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


   (at-media {:max-width "600px"}
             [:&
              {:width "100vw"
               :height (data :mobile-height)}

              [:.content.left
               {:width "100vw"
                :box-sizing "border-box"
                :display "flex"
                :flex-direction "column"
                :justify-content "center"
                :align-items "center"
                :text-align "center"
                :padding "0 1em"}

               [:h1
                {:font-size "1.65em"
                 :white-space "normal"}]


               ["> p"
                {:font-size "1.15em"
                 :color (get-in data [:sub-heading :mobile-color])
                 :white-space "normal"
                 :text-align "center"}]]

              [:.content.right
               [:div.image
                {:background-image (str "url(" (get-in data [:featured-image :mobile-url]) ")")
                 :max-width "448px"
                 :size "cover"}
                [:.caption
                 {:display "none"}]]]])
   (at-media {:max-width "320px"}
     [:&
      {:width "100vw"
       :height "500px"}
      [:.right
       [:.image
        {:width "320px"
         :height "500px"}]]])])








(defn hero [data]
  [:section.hero-testimonial
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
     [:div.image
      [:div.caption
       [:div.coach-name "Derrell Levy\nIn-Tech High Performance Training"]
       [:a.coach-link {:href "http://intech.getrookie.com"} "See how Intech uses Rookie"]]]]]])


(defmethod template "hero-testimonial" [_]
  {:css styles
   :component hero})
