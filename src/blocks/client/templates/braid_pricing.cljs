(ns blocks.client.templates.braid-pricing
  (:require
    [blocks.client.template :refer [template]]
    [blocks.client.templates.mixins :refer [button-mixin]]
    [garden.stylesheet :refer [at-media]]))

(def data)


(defn styles [data]
  [:.braid-pricing
   {:background-color (data :background)
    :padding "2em 0"}

   [:>h1
    {:text-align "center"
     :font-weight "bolder"
     :font-size "2em"}]

   [:>.plans
    {:display "flex"
     :justify-content "center"
     :align-items "center"
     :margin "3em 0"}

    [:>.plan
     {:background "white"
      :box-shadow "0 8px 15px 0px rgba(0,0,0,0.05)"
      :margin "0 2em"
      :width "40%"
      :max-width "21em"}

     [:>.header
      {:background "#3D75FB"
       :color "white"
       :padding "2em"}

      [:>h1
       {:text-align "center"
        :font-size "2em"
        :margin-bottom "0.5em"}]

      [:>h2
       {:text-align "center"}]]

     [:>.content
      {:display "flex"
       :flex-direction "column"
       :align-items "center"
       :padding "0 2em 2em"}

      [:>.price
       {:min-height "8em"
        :text-align "center"
        :display "flex"
        :flex-direction "column"
        :justify-content "center"}

       [:>span
        {:display "block"
         :font-size "1em"}]

       [:>span:first-child
        {:font-size "1.5em"}]]

      [:>.features
       [:>.feature]]

      [:>.button
       {:width "100%"
        :margin-top "2em"}]]]

    (map-indexed (fn [index plan]
                   [(str ">.plan:nth-child(" (+ index 1) ")")
                    [:>.content
                     [:>.button
                      (button-mixin (get-in plan [:button :colors] {}))]]])
                 (data :plans))]

   [:>p
    {:text-align "center"}
    [:>a
     {:font-weight 600
      :color (get-in data [:contact :color])}]]])

(defn component [data]
  [:div.braid-pricing
   [:h1 "Pick your plan:"]
   [:div.plans
    (for [plan (data :plans)]
      [:div.plan
       [:div.header {:style {:background-color (plan :color)}}
        [:h1 (plan :title)]
        [:h2 (plan :subtitle)]]
       [:div.content
        [:div.price
         (for [line (plan :price)]
           [:span line])]
        [:div.features
         (for [feature (plan :features)]
           [:div.feature feature])]
        [:a.button {:href (get-in plan [:button :url])}
         (get-in plan [:button :text])]]])]
   [:p
    "Need more features, custom integrations or want to host it yourself? "
    [:a {:href (get-in data [:contact :email])} "Contact us"]
    "."]])

(defmethod template "braid-pricing" [_]
  {:css       styles
   :component component})
