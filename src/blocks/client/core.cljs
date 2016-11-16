(ns blocks.client.core
  (:require
    [clojure.string :as string]
    [reagent.core :as reagent]
    [re-frame.core :refer [reg-event-db
                           reg-sub
                           dispatch-sync
                           subscribe]]
    [garden.core :refer [css]]
    [garden.stylesheet :refer [at-import]]
    [blocks.client.template :refer [template]]
    [blocks.client.templates]))

(reg-event-db
  :set-data
  (fn [_ [_ data]]
    data))

(reg-sub
  :page
  (fn [state _]
    (state :page)))

(defn google-fonts-import [data]
   (when-let [google-fonts (->> (get-in data [:styles :fonts])
                                vals
                                (filter (fn [font]
                                          (= "google" (font :source))))
                                not-empty)]
     (at-import (str "//fonts.googleapis.com/css?family="
                     (->> google-fonts
                          (map (fn [font]
                                 (str (string/replace (font :name) #" " "+")
                                      ":" (font :weight))))
                          (string/join "|"))))))

(defn styles [data]
  [(at-import "//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css")

   (google-fonts-import data)

   [:body :input
    {:font-family [(str "\"" (get-in data [:styles :fonts :body :name]) "\"") "sans-serif"]
     :font-weight (get-in data [:styles :fonts :body :weight])
     :line-height "1.25" }]

   [:h1 :h2 :h3 :h4 :h5
    {:font-family [(str "\"" (get-in data [:styles :fonts :headings :name]) "\"") "sans-serif"]
     :font-weight (get-in data [:styles :fonts :headings :weight])}]

   [:.block
    {:overflow "hidden"}

    [:.content
     {:max-width "1000px"
      :position "relative"
      :margin "0 auto"}]]])

(defn app-view []
  (let [page (subscribe [:page])]
    (fn []
      (let [blocks (->> (@page :blocks)
                        (map-indexed (fn [index b]
                                       (assoc b :id (str "block-" index)))))]
        [:div.app
         [:div.styles
          [:style {:type "text/css"
                   :dangerouslySetInnerHTML
                   {:__html (css (styles @page))}}]
          (for [block blocks]
            ^{:key (block :id)}
            [:style {:type "text/css"
                     :dangerouslySetInnerHTML
                     {:__html (css
                                {:auto-prefix #{:transition
                                                :flex-direction
                                                :flex-shrink
                                                :align-items
                                                :animation
                                                :flex-grow}
                                 :vendors ["webkit" "moz"]}
                                [(str "#" (block :id))
                                 ((:css (template (block :template))) (block :data))])}}])]
         (for [block blocks]
           ^{:key (block :id)}
           [:div {:id (block :id) :class "block"}
            [(:component (template (block :template))) (block :data)]])]))))

(defn render []
  (reagent/render [app-view]
                  (js/document.getElementById "app")))

(defn ^:export run
  []
  (dispatch-sync [:set-data (cljs.reader/read-string (.-innerHTML (js/document.getElementById "data")))])
  (render))

