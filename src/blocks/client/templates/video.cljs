(ns blocks.client.templates.video
  (:require
    [reagent.core :as r]
    [blocks.client.template :refer [template]]
    [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.video
   {:padding "2em"
    :background (get-in data [:styles :background])}

   [:.content
    {:display "flex"
     :flex-direction  "column"
     :justify-content "center"
     :align-items "center"
     :text-align "center"}

    [:h1
     {:color (get-in data [:heading :color])
      :font-size "1.75rem"
      :font-weight "bolder"
      :position "relative"
      :z-index 0}]

    [:h2
     {:color (get-in data [:subtitle :color])
      :font-size "1.25em"
      :padding-bottom "2em"}]

    [:.video
     {:width "100%"
      :max-height "80vh"}

     [:iframe
      {:background "black"}]]

    [:p {:padding "1em"
         :color "#fff"}]]
   (at-media {:max-width "700px"}
     [:&
      {:padding "2em 0"}
      [:.content
       [:.video
        [:a.thumbnail
         [:img
          {:width "90vw"}]]]]])])

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
       [:div.content
        [:h1 (get-in data [:heading :title])]
        [:h2 (get-in data [:subtitle :text])]
        [:div.video
         (if @play?
           [:iframe {:src (embed-url (get-in data [:video :id]))
                     :width "100%"
                     :height "100%"
                     :frameborder 0
                     :allow-full-screen true}]
           [:a.thumbnail {:href (video-url (get-in data [:video :id]))
                          :on-click (fn [e]
                                      (.preventDefault e)
                                      (reset! play? true))}
            [:img {:src (or (get-in data [:video :thumbnail])
                            (thumbnail-url (get-in data [:video :id])))}]])]]])))

(defmethod template "video" [_]
  {:css       styles
   :component component})

