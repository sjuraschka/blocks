(ns blocks.client.templates.promotion
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.promotion
   {:margin "3rem"
    :border-radius "10px"
    :padding "3em"
    :background (get-in data [:styles :background :color])
    :background-image  (str "url(" (get-in data [:styles :background :url]) ")")
    :background-size "cover"
    :text-align "center"
    :display "flex"
    :justify-content "center"
    :box-sizing "border-box"}

   [:.text
    {:text-align "center"
     :display "flex"
     :flex-direction "column"
     :justify-content "center"
     :align-items "center"
     :max-width "35em"}

    [:.logo {:height "1.5em"}]
    [:.headline
     {:color (get-in data [:styles :heading :color])
      :font-size (get-in data [:styles :heading :size])
      :margin "0.5em"}]

    [:.about
     {:padding "1em"
      :letter-spacing "0.05em"
      :color (get-in data [:styles :about :color])
      :text-transform (get-in data [:styles :about :transform])
      :font-size (get-in data [:styles :about :size])
      :text-decoration "none"}]

    [:.sub-heading
     {:color (get-in data [:styles :sub-heading :color])
      :margin-bottom "2em"
      :font-size (get-in data [:styles :sub-heading :size])
      :font-style "italic"
      :letter-spacing "0.05em"}]

    [:.button
     {:background "#2F60FF"
      :border-radius "100px"
      :padding "1em 2em"
      :color "#fff"
      :text-transform "uppercase"
      :letter-spacing "0.05em"
      :text-decoration "none"}
     [:&:hover
      {:background "#0037ea"}]]]])

(defn component [data]
  [:div.promotion
   [:div.text
    [:img.logo {:src (data :image)}]
    [:a.about {:href (get-in data [:content :link])}
              (get-in data [:content :headline])]
    [:h2.headline (get-in data [:content :promo-text])]
    [:p.sub-heading (get-in data [:content :sub-heading])]
    [:a.button {:href (get-in data [:content :link])}
               (get-in data [:button :text])]]])

(defmethod template "promotion" [_]
  {:css styles
   :component component})

