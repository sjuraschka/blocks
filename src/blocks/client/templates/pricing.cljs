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
    {:padding-top "4rem"
     :font-size "2.2em"
     :font-weight "bolder"
     :color (get-in data [:heading :title :color])}]
   [:.section-subtitle
    {:font-size "1.25em"
     :color (get-in data [:heading :subtitle :color])
     :padding-bottom "2rem"}]
   [:.options
    {:display         "flex"
     :justify-content "space-between"
     :align-items "center"
     :padding         "2rem"
     ;:align-items     "stretch"
     :color (get-in data [:description-color])}

    [:.option
     {:background     "#fff"
      :box-shadow     [[0 "1px" "2px" 0 "#ccc"]]
      :border-radius  "3px"
      :border         "1px solid #eee"
      :box-sizing     "border-box"
      :min-width      "18em"
      :height         "25em"
      :margin-right   "2em"
      :text-align     "center"
      :display        "flex"
      :flex-direction "column"
      :align-items    "center"}

     [:&:last-child
      {:margin-right 0}]

     [:&.highlight
      {:border "2px solid #f17130"
       :height "30em"}

      [:.featured
       {:background "#f17130"
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
      [:h2
       {}]

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

      [:input {:border-radius "0.25em"
               :background "#f4f8fb"
               :margin "0 0.25em 0.25em 0.25em"
               :border "1px solid #ccc"
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
        :font-weight "bolder"
        }]

      [:.feature
       {:padding-top "0.25em"
        :left       0
        :right      0}]]]]

   (at-media {:max-width "950px"}
             [:section.pricing
              {:display "flex"
               :flex-direction "column"
               :flex-wrap "wrap"}
              [:.options
               {:display "flex"
                :flex-direction "column"
                :flex-wrap "wrap"}]])])


(defn component [data]
  [:section.pricing
   [:div.section-title (get-in data [:heading :title :text])]
   [:div.section-subtitle(get-in data [:heading :subtitle :text])]
   [:div.options
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
         (for [feature (option :features)]
           [:div.feature feature])]
       ;[:a.button "Start Trial"]
       ])]])

(defmethod template "pricing" [_]
  {:css       styles
   :component component})