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
    :flex-direction (get-in data [:background :direction])
    :justify-content  "flex-end"
    :background (get-in data [:background :color])
    :width            "100vw"
    :min-height       (data :min-height)}


   [:.image
    {:background-image  (str "url(" (get-in data [:background :image-url]) ")")
     :background-size   (get-in data [:styles :background-size])
     :background-repeat "no-repeat"
     :position          "absolute"
     :height            (get-in data [:styles :image-height])
     :width             (get-in data [:styles :image-width])
     :left              (get-in data [:styles :left])
     :right             (get-in data [:styles :right])
     :bottom            0}]


   [:.testimonial
    {:box-sizing      "border-box"
     :position        "relative"
     :width           "40vw"
     :display         "flex"
     :flex-direction  "column"
     :padding         "3em"
     :margin-left     "8em"
     :align-items     "flex-start"
     :justify-content "center"
     :color           (get-in data [:styles :color])}

    [:.title
     {:font-size      "1.5em"
      :padding-bottom "1rem"
      :text-transform (get-in data [:heading :transform])}]

    [:.subtitle
     {:max-width  "30rem"
      :font-size  "0.8rem"
      :text-align "left"}]]

   (at-media {:max-width "800px"}
             [:&
              [:.image]
              [:.testimonial
               {:margin-left 0
                :background "rgba(240, 240, 240, 0.55)"
                :width      "100vw"}]])

   (at-media {:max-width "450px"}
             [:&

              [:.image
               {:background-image (str "url(" (get-in data [:background :mobile-url]) ")")}]])])



(defn component [data]
  [:div.image-testimonial
   [:div.image]

   [:div.testimonial
    [:h1.title (get-in data [:heading :text])]
    [:p.subtitle (get-in data [:label :text])]]])


(defmethod template "image-testimonial" [_]
  {:css       styles
   :component component})
