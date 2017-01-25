(ns blocks.client.core
  (:require
    [reagent.core :as reagent]
    [blocks.client.styles :refer [styles-view page-styles]]
    [blocks.client.template :refer [template]]
    [blocks.client.templates]))

(defn page-view [page]
  (let [blocks (->> (page :blocks)
                    (map-indexed (fn [index b]
                                   (assoc b :id (str "block-" index)))))]
    [:div.app
     [:div.styles
      [styles-view (page-styles page)]
      (for [block blocks]
        ^{:key (block :id)}
        [styles-view [(str "#" (block :id))
                      ((:css (template (block :template))) (block :data))]])]
     (for [block blocks]
       ^{:key (block :id)}
       [:div {:id (block :id) :class "block"}
        [(:component (template (block :template))) (block :data)]])]))

(defn get-data []
  (cljs.reader/read-string (.-innerHTML (js/document.getElementById "data"))))

(defn ^:export run []
  (reagent/render [page-view (:page (get-data))]
                  (js/document.getElementById "app")))
