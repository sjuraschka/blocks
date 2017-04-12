(ns blocks.client.templates.pricing
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [button-mixin]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:section.pricing
   {:display "flex"
    :flex-direction "column"
    :justify-content "center"
    :align-items "center"
    :background-color (data :background)}
   [:.section-title
    {:text-align "center"
     :padding-top "4rem"
     :font-size (get-in data [:heading :title :font-size])
     :font-weight "bolder"
     :color (get-in data [:heading :title :color])}]
   [:.section-subtitle
    {:font-size "1.25em"
     :color (get-in data [:heading :subtitle :color])
     :padding "1rem"
     :text-align "center"}]
   [:.options
    {:display         "flex"
     :justify-content "space-between"
     :align-items "center"
     :padding         "2rem"
     ;:align-items     "stretch"
     :color (get-in data [:description-color])}

    [:.option
     {:background     "#fff"
      :border-radius  "3px"
      :box-shadow     "0 8px 15px 0px rgba(0,0,0,0.05)"
      :border         "1px solid #fafafa"
      :box-sizing     "border-box"
      :min-width      "20em"
      :height         "25em"
      :margin-right   "2em"
      :text-align     "center"
      :display        "flex"
      :flex-direction "column"
      :align-items    "center"}

     [:&:last-child
      {:margin-right 0}]

     [:&.highlight
      {;:border "3px solid #3D75FB"
       :height "30em"}

      [:.featured
       {:background "#3D75FB"
        :width "100%"
        :padding "0.25em"
        :box-sizing "border-box"
        :color "#fff"}]]

     [:.about
      {:height "15%"
       :padding "1em 1em 5em 1em"}

      [:h1
       {:color       (get-in data [:title-color])
        :font-weight "bolder"
        :font-size   "1.25em"}]
      [:h2]


      [:.price
       {:margin-top "1em"}

       [:.amount
        {:color     "#384d51"
         :font-size "2.5em;"}

        [:&::before
         {:content "\"$\""
          :font-size "0.5em"
          :line-height "1.5em"
          :vertical-align "top"}]]

       [:input {:border-radius "3px"
                :background "#f4f7ff"
                :margin "0 0.25em 0.25em 0.25em"
                :border "1px solid #3D75FB"
                :font-size "2.25rem"
                :color "#384d51"
                :width "7rem"
                :box-sizing "border-box"
                :text-align "center"}]

       [:a.button
        (button-mixin (get-in data [:button-colors]))
        [:&
         {:border-radius "0.25em"
          :font-size "1em"
          :padding "0.5em 1em"
          :margin "10px"
          :text-transform "uppercase"}]]

       [:.text
        {:color "#5f6c73"
         :font-size "0.8em"}]]]

     [:.features
      {:width       "100%"
       :padding-top "2em"
       :font-size "0.9em"}
      [:h1
       {:color "#5f6c73"
        :font-weight "bolder"}]


      [:.feature
       {:padding-top "0.25em"
        :left       0
        :right      0}]]]]

   (at-media {:max-width "750px"}
     [:&
      [:.options
       {:flex-direction "column"
        :padding "1em"}
       [:.option
        {:margin 0
         :width "90vw"
         :margin-bottom "2em"}]]])])

(defn component [data]
  [:section.pricing
   [:div.section-title (get-in data [:heading :title :text])]
   [:div.section-subtitle(get-in data [:heading :subtitle :text])]
   (into
     [:div.options]
     (for [option (data :options)]
       [:div.option {:class (when (option :highlight) "highlight")
                     :style {:width (str (/ 100 (count (data :options))) "%")}}
        [:div.featured (option :featured-text)]
        [:div.about
         [:h1 (option :title)]
         [:h2 (option :subtitle)]
         (let [price (option :price)]
           (cond
             (price :button)
             [:div.price
              [:a.button (get-in price [:button :text])]
              [:div.text (price :text)]]

             (price :pwyc)
             [:div.price
              [:div.amount
               [:input {:value (price :amount)}]]
              [:div.text (price :text)]]

             :else
             [:div.price
              [:div.amount (price :amount)]
              [:div.text (price :text)]]))]

        [:div.features
         [:h1.features-title (option :features-title)]
         (into
           [:div]
           (for [feature (option :features)]
             [:div.feature feature]))]]))])
        ;[:a.button "Start Trial"]


(defmethod template "pricing" [_]
  {:css       styles
   :component component})
