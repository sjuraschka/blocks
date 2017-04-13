(ns blocks.client.templates.cta
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.partials.email-field :as email-field]
            [garden.stylesheet :refer [at-media]]))


(defn styles [data]
  [:.cta
   {:padding "0 4em"
    :padding-top "4em"
    :padding-bottom "2em"
    :background (get-in data [:background :color])
    :text-align "center"}

   [:.content

    [:h1
     {:color (get-in data [:heading :color])
      :font-weight "bolder"
      :font-size "1.75rem"
      :-webkit-font-smoothing "antialiased"}]

    [:p
     {:color (get-in data [:description :color])
      :padding-top "1rem"
      :font-size "1.25rem"
      :margin-bottom "2rem"}]

    (email-field/styles (data :form))]

   (at-media {:max-width "700px"}
     [:&
      {:padding "0 2rem"
       :padding-top "2rem"
       :padding-bottom 0}])])

(defn component [data]
  [:section.cta
    [:a {:name (data :anchor)}]
    [:div.content
      [:div.text
        [:h1 (get-in data [:heading :text])]
        [:p  (get-in data [:description :text])]]
      [email-field/component (data :form)]]])

(defmethod template "cta" [_]
  {:css styles
   :component component})
