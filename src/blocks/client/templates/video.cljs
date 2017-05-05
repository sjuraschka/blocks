(ns blocks.client.templates.video
  (:require
    [reagent.core :as r]
    [blocks.client.template :refer [template]]
    [blocks.client.templates.mixins :refer [button-mixin fontawesome-mixin]]
    [garden.stylesheet :refer [at-media]]))

(def height "80vh")

(defn styles [data]
  [:>.video
   {}

   [:>.content
    {:padding "3em 0"
     :box-sizing "border-box"
     :height height
     :background (get-in data [:styles :background])
     :background-size "cover"
     :background-repeat "no-repeat"
     :max-width "inherit"
     :display "flex"
     :align-items "center"
     :justify-content "space-between"}

    [:>button
     {:white-space "nowrap"
      :text-transform "uppercase"
      :margin-right "-5em"
      :letter-spacing "0.05em"
      :z-index 100}
     (button-mixin (get-in data [:button :colors] {}))

     [:&::after
      {:margin-left "0.5em"}
      (fontawesome-mixin \uf04b)]]

    [:>.spacer
     {:width "30%"
      :min-width "3em"
      :flex-grow 2}]

    [:>img
     {:margin-bottom "-1em"
      :margin-right "-2em"
      :height "100%"}]]

   [:iframe
    {:background (get-in data [:video :background])
     :height height}]

   (at-media {:max-width "700px"}
     [:&
      [:>.content
       [:>button
        {:margin-right "-25%"}]]])])

(defn video-type [id]
  (cond
    (re-matches (re-pattern "([0-9]){8,9}") id) :vimeo
    (re-matches (re-pattern "([a-zA-Z0-9_-]){11}") id) :youtube))

(defn embed-url [id]
  (case (video-type id)
    :youtube (str "//www.youtube.com/embed/" id "?rel=0&autoplay=1&autohide=1&modestbranding=1&showinfo=0")
    :vimeo (str "//player.vimeo.com/video/" id "?badge=0&byline=0&portrait=0&title=0")))

(defn video-url [id]
  (case (video-type id)
    :youtube (str "https://www.youtube.com/watch?v=" id)))

(defn thumbnail-url [id]
  (case (video-type id)
    :youtube (str "//i.ytimg.com/vi/" id "/mqdefault.jpg")
    :vimeo (str  "//i.vimeocdn.com/video/" id ".webp?mw=400&mh=400&q=30")))

(defn component [data]
  (let [play? (r/atom false)]
    (fn [data]
      [:div.video
       [:a {:name (data :anchor)}]
       (if @play?
         [:iframe {:src (embed-url (get-in data [:video :id]))
                   :width "100%"
                   :height "100%"
                   :auto-play true
                   :frameborder 0
                   :allow-full-screen true}]

         [:div.content
          [:div.spacer]
          [:button {:on-click (fn [e]
                                (.preventDefault e)
                                (reset! play? true))}
           (get-in data [:button :text])]
          [:img {:src (get-in data [:image :url])}]])])))

(defmethod template "video" [_]
  {:css       styles
   :component component})

