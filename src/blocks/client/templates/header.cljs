(ns blocks.client.templates.header
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(def pad "3rem")

(defn styles [data]
  [:header
   (when (data :overlay?)
     {:position "absolute"
      :top      0
      :left     0
      :right    0
      :z-index  100})

   [:.content
    {:display         "flex"
     :width           "100%"
     :max-width       "100%"
     :position        (data :position)
     :padding         (data :padding)
     :box-sizing      "border-box"
     :background      (get-in data [:background :color])
     :justify-content "space-between"
     :align-items     "center"
     :box-shadow  (data :shadow)}

    [:.logo
     {:color       (get-in data [:logo :color])
      :white-space "nowrap"}

     [:img
      {:display        "inline-block"
       :vertical-align "middle"
       :height         (data :height)
       :margin-right   "0.5em"}]

     [:h1
      {:display        "inline-block"
       :font-size      "1.25em"
       :line-height    "2rem"
       :vertical-align "middle"}]

     [:.version
      {:display        "inline-block"
       :opacity        "0.5"
       :font-size      "0.5em"
       :line-height    "2rem"
       :vertical-align "sub"
       :margin-left    "0.5em"
       :text-transform "uppercase"
       :letter-spacing "0.1em"}]]

    [:nav
     {:text-align "right"}

     [:a
      {:color           (data :color)
       :opacity         0.6
       :text-decoration "none"
       :margin-left     "2em"
       :transition      "opacity 0.1s ease-in-out"
       :line-height     "2em"}

      [:&:hover
       {:opacity 1}]

      ["&[data-icon]:before"
       {:content      "attr(data-icon)"
        :font-family  "FontAwesome"
        :margin-right "0.25em"}]

      [:&.button
       {:display       "inline-block"
        :border        [["1px" "solid" (data :color)]]
        :border-radius "0.25em"
        :height        "2em"
        :padding       "0 0.5em"}]]]]

   (at-media {:max-width "550px"}
             [:.content
              {:display         "flex"
               :flex-direction  "column"
               :justify-content "center"
               :align-items     "center"}

              [:nav
               {:display "none"}]])])

(defn component [data]
  [:header
   [:div.content
    [:a.logo {:href (get-in data [:logo :url])}
     [:img {:src (get-in data [:logo :image-url])}]
     [:h1 (data :title)]
     (when (data :version)
       [:span.version (data :version)])]
    (into
      [:nav]
      (for [link (data :menu)]
        [:a {:href      (link :url)
             :class     (link :style)
             :data-icon (link :icon)}
         (link :text)]))]])

(defmethod template "header" [_]
  {:css       styles
   :component component})
