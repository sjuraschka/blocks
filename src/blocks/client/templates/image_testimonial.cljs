(ns blocks.client.templates.image-testimonial
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]
            [blocks.client.templates.partials.email-field :as email-field]
            [garden.stylesheet :refer [at-media]]))


(defn styles [data]
  [:.image-testimonial
   {:box-sizing       "border-box"
    :position         "relative"
    :display          "flex"
    :justify-content  "flex-end"
    :background-image "linear-gradient(45deg, #9BFFF3 27%, #AEEAE6 100%)"
    :width            "100vw"
    :min-height       "400px"}


   [:.image
    {:background-image  (str "url(" (get-in data [:background :image-url]) ")")
     :background-repeat "no-repeat"
     :position          "absolute"
     :height            "100%"
     :width             "700px"
     :left              0
     :bottom            0}]


   [:.testimonial
    {:box-sizing      "border-box"
     :position        "relative"
     :width           "40vw"
     :display         "flex"
     :flex-direction  "column"
     :padding         "2em"
     :align-items     "flex-start"
     :justify-content "center"
     :color           "#232b69"}

    [:.title
     {:font-size      "1.75em"
      :padding-bottom "1.5rem"
      :text-transform "uppercase"}]

    [:.subtitle
     {:max-width  "30rem"
      :font-size  "0.8rem"
      :text-align "left"}]]

   (at-media {:max-width "800px"}
             [:&
              [:.image]
              [:.testimonial
               {:background "rgba(240, 240, 240, 0.55)"
                :width      "100vw"}]])

   (at-media {:max-width "450px"}
             [:&

              [:.image
               {:background-image (str "url(" (get-in data [:background :mobile-url]) ")")}]])])



(defn component [data]
  [:div.image-testimonial
   [:div.image]

   [:div.testimonial
    [:h1.title "\"With my USA hockey combines I can now train participants beyond the one weekend a year I see them in person.\""]
    [:p.subtitle "Derrell Levy, In-Tech High Performance Training"]]])


(defmethod template "image-testimonial" [_]
  {:css       styles
   :component component})
