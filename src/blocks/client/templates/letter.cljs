(ns blocks.client.templates.letter
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.letter
   {:background (data :background)}
   [:.content
    {:display "flex"
     :flex-direction "column"
     :justify-content "center"
     :align-items "center"
     :padding "1em"}

    [:h1.title
     {:font-size "1.85em"
      :padding "1em"
      :color (get-in data [:heading :title-color])
      :font-weight "bolder"}]

    [:.lower
     {:display "flex"
      :width "90%"}

     [:div.left
      {:position "relative"
       :width "60%"}

      [:.text-content
       {:padding-bottom "1em"
        :line-height "1.25em"
        :color (get-in data [:paragraph :color])}
       [:span
        {:font-weight 600}]]
      [:.signature
       {:height "4em"
        :padding "1em 2em"}]]

     [:div.spacer
      {:width "5%"}]
     [:.right
      {:width "35%"}]]]

   (at-media {:max-width "575px"}
             [:.content
              [:.text
               {:column-count 1
                :column-gap 0
                :padding 0
                :text-align "center"}]])])

(defn component [data]
  [:div.letter
   [:div.content
    [:h1.title (get-in data [:heading :text])]

    [:div.lower
     [:div.left
      [:div.text-content {:dangerouslySetInnerHTML
                           {:__html (get-in data [:paragraph :text])}}]
      [:img.signature {:src "/braid/images/braid-chat-signature.png"}]]
     [:div.spacer]
     [:div.right
      [:img {:src "/braid/images/braid-chat-thread.png"}]]]]])

(defmethod template "letter" [_]
  {:css       styles
   :component component})