(ns blocks.client.templates.pricing
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:section.pricing
   [:.options
    {:display "flex"
     :justify-content "space-between"
     :padding "3em"
     :align-items "stretch"}

    [:.option
     {:background "#fff"
      :box-shadow [[0 "1px" "2px" 0 "#ccc"]]
      :border-radius "3px"
      :padding "1em"
      :box-sizing "border-box"
      :margin-right "2em"
      :display "flex"
      :flex-direction "column"
      :align-items "center"}

     [:&:last-child
      {:margin-right 0}]

     [:&.highlight
      {:border "1px solid green"}]

     [:h1
      {:color (get-in data [:title-color])
       :font-weight "bolder"
       :font-size "1.25em"}]

     [:.price

      [:.amount

       [:&::before
        {:content "\"$\""
         }
        ]
       ]]

     [:.features
      {:padding-top "1em"}
      [:.feature ]]

     ]]])


(defn component [data]
  [:section.pricing
   [:div.options
    (for [option (data :options)]
      [:div.option {:class (when (option :highlight) "highlight")
                    :style {:width (str (/ 100 (count (data :options))) "%")}}
       [:h1 (option :title)]
       [:h2 (option :subtitle)]
       (let [price (option :price)]
         (cond
           (price :button)
           [:div.price
            [:button (get-in price [:button :text])]]

           (price :pwyc)
           [:div.price
            [:div.amount
             [:input {:value (price :amount)}]]]

           :else
           [:div.price
            [:div.amount (price :amount)]]))
       [:div.features
        (for [feature (option :features)]
          [:div.feature feature])]])]])

(defmethod template "pricing" [_]
  {:css styles
   :component component})