(ns blocks.client.templates.value-banner
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin]]
            [garden.stylesheet :refer [at-media]]))

(def pad "4em")

(defn styles [data]
  [:.value-banner
   {:background "white"}

   [:div.banner
    {:margin (data :block-margin)
     :display "flex"
     :position "relative"
     :background (get-in data [:background :color])
     :height (get-in data [:background :height])}
    [:&.right
     {:flex-direction "row-reverse"}]

    [:div.spacer
     {:width "15%"}]

    [:div.banner-image
     {:height "592px"
      :width "534px"
      :background-repeat "no-repeat"
      :position "relative"
      :background-image (str "url(" (get-in data [:background :image]) ")")}]

    [:.text-container
     {:width "18em"
      :padding "1em"
      :align-self "center"
      :color (get-in data [:styles :text-color])}

     [:.title
      {:font-weight 600
       :font-size "1.15em"
       :font-family (data :font-family)}]

     [:.sub-text
      {:margin-top "0.5em"
       :font-family (data :font-family)
       :font-weight 300}]]]

   [:.text
    {:min-width "15em"
     :display "flex"
     :flex-direction "column"
     :max-width "30em"
     :align-items (get-in data [:heading :align])
     :text-align (data :alignment)
     :flex-basis "30%"
     :padding "2em"}]])

(defn component [data]
  [:section.value-banner
   [:div.banner {:class (data :position)}
    [:div.banner-image]
    [:div.spacer]
    [:div.text-container
     [:h1.title (get-in data [:heading :text])]
     [:h2.sub-text (get-in data [:sub-text :text])]]]])

(defmethod template "value-banner" [_]
  {:css styles
   :component component})

