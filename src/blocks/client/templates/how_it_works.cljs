(ns blocks.client.templates.how-it-works
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]
            [blocks.client.templates.partials.email-field :as email-field]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.how-it-works
   {:background    "#fff"
    :box-sizing    "border-box"
    :border-bottom "1px solid #eee"}
    ;:height        "115vh"}

   [:&::before
    {:content          "\"\""
     :display          "block"
     :height           "62vh"
     :position         "relative"
     :width            "100vw"
     :z-index          1}]

   [:.content
    {:margin-top     "-55vh"
     :z-index        2
     :box-sizing     "border-box"
     :width          "100vw"
     :display        "flex"
     :flex-direction "column"
     :align-items    "center"
     :color          "#2E3944"}

    [:.title
     {:font-size      "1.75em"
      :box-sizing     "border-box"
      :color          "#2E3944"
      :letter-spacing "0.1em"
      :padding-bottom "1.5rem"
      :text-transform "uppercase"
      :text-decoration "none"}]

    [:.subtitle
     {:max-width  "30em"
      :text-align "center"}]

    [:.button
     {:background     "#232B69"
      :box-shadow     "0px 0px 10px 0px rgba(223,222,222,0.09)"
      :border-radius  "5px"
      :margin         "1.5rem"
      :padding        "0.75rem 3rem"
      :color "#fff"
      :text-decoration "none"
      :font-weight    "bolder"
      :font-size      "0.75rem"
      :text-transform "uppercase"}

     [:&:hover
      {:background (get-in data [:button :bg-hover])}]]

    [:img
     {:height "60vh"}]


    [:.columns
     {:padding-top     ""
      :display         "flex"
      :text-align      "center"
      :justify-content "space-between"
      :width           "75vw"}

     [:.column
      {:width          "30vw"
       :box-sizing     "border-box"
       :text-align     "center"
       :display        "flex"
       :flex-direction "column"
       :align-items    "center"
       :padding-bottom "1em"}

      [:a
       {:text-decoration "none"
        :color "#616161"}

       [:&:hover
        {:background (get-in data [:detail-button :bg-hover])}]


       [:.step]
       {:font-size      "0.8em"
        :color          "#616161"
        :text-transform "uppercase"
        :padding        "0.75rem"}]

      [:.subtitle {:color     "#616161"
                   :font-size "0.75em"
                   :padding   "1em"}
       [:&::after
        (fontawesome-mixin \uf105)
        {:padding-left "0.5em"
         :font-size    "0.65em"}]]]]]

   (at-media {:max-width "840px"}

             [:&
              {:height "100vh"}]

             [:&::before
              {:height "100vh"}]

             [:.content
              {:margin-top "-80vh"}

              [:.title
               {:max-width "16em"}]

              [:.subtitle
               {:max-width "20em"}]

              [:video {:width      "80vw"
                       :height     "initial"
                       :min-width  "368px"
                       :min-height "208.55px"}]

              [:.title
               {:padding-bottom 0
                :text-align     "center"
                :font-size      "1.3em"
                :font-weight    "bold"
                :text-transform "uppercase"}]

              [:p.subtitle
               {:color "#blue"}]

              [:.columns
               {:display "none"
                :width   "100vw"
                :height  "100vh"}]])])

(defn component [data]
  [:div.how-it-works
   [:div.content
    [:h1.title "How you'll use Rookie with your athletes"]
    [:p.subtitle "Whether your clients are in the local gym, on social media,\nor across the globe, Rookie will help you train them."]
    [:a.button {:href "./how-it-works/"} "Learn How It Works"]
    [:video {:src "/rookie/videos/how-it-works-small.m4v"
             :auto-play true
             :preload "auto"
             :fullscreen false
             :muted true
             :loop true}]
    (into
      [:div.columns]
      (for [column (data :columns)]
        [:div.column
         [:a {:href (column :url)} [:h1.step (column :title)]
             [:p.subtitle (column :subtitle)]]]))]])

(defmethod template "how-it-works" [_]
  {:css       styles
   :component component})
