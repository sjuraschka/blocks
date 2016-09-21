(ns blocks.client.templates.pricing
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [button-mixin]]))

(defn styles [data]
  [:section.pricing
   [:.options
    {:display         "flex"
     :justify-content "space-between"
     :padding         "3em"
     :align-items     "stretch"
     :color (get-in data [:description-color])}

    [:.option
     {:background     "#fff"
      :box-shadow     [[0 "1px" "2px" 0 "#ccc"]]
      :border-radius  "5px"
      :border         "1px solid #eee"
      :box-sizing     "border-box"
      :margin-right   "2em"
      :text-align     "center"
      :display        "flex"
      :flex-direction "column"
      :align-items    "center"}

     [:&:last-child
      {:margin-right 0}]

     [:&.highlight
      {:border "3px solid #65a8b6"}]

     [:.about
      {:height "15%"
       :padding "1em 1em 5em 1em"}

      [:h1
      {:color       (get-in data [:title-color])
       :font-weight "bolder"
       :font-size   "1.25em"}]
      [:h2
       {:font-weight "bolder"}]

     [:.price

      [:.amount
       {:color     "#384d51"
        :font-size "2.5em;"}

       [:&::before
        {:content "\"$\""}]]

      [:button
       {:background "#f17130"
        :font-size "1em"
        :padding "0.3em"
        :border-radius "0.25em"
        :margin "1em"
        :border "none"
        :color "#fff"}]

      [:.text
       {:color "#ccc"}]]]

     [:.features

      {:width       "100%"
       :padding-top "1em"}
      [:.feature
       {:border-top "1px solid #eee"
        :left       0
        :right      0}]]]]])


(defn component [data]
  [:section.pricing
   [:div.options
    (for [option (data :options)]
      [:div.option {:class (when (option :highlight) "highlight")
                    :style {:width (str (/ 100 (count (data :options))) "%")}}
       [:div.about
        [:h1 (option :title)]
        [:h2 (option :subtitle)]
        (let [price (option :price)]
          (cond
            (price :button)
            [:div.price
             [:button (get-in price [:button :text])]
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
         (for [feature (option :features)]
           [:div.feature feature])]])]])

(defmethod template "pricing" [_]
  {:css       styles
   :component component})