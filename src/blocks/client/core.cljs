(ns blocks.client.core
  (:require
    [reagent.core :as reagent]
    [re-frame.core :refer [reg-event-db
                           reg-sub
                           dispatch-sync
                           subscribe]]
    [blocks.client.styles :refer [styles-view page-styles]]
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

(defn app-view []
  (let [page (subscribe [:page])]
    (fn []
      (let [blocks (->> (@page :blocks)
                        (map-indexed (fn [index b]
                                       (assoc b :id (str "block-" index)))))]
        [:div.app
         [:div.styles
          [styles-view (page-styles @page)]
          (for [block blocks]
            ^{:key (block :id)}
            [styles-view [(str "#" (block :id))
                          ((:css (template (block :template))) (block :data))]])]
         (for [block blocks]
           ^{:key (block :id)}
           [:div {:id (block :id) :class "block"}
            [(:component (template (block :template))) (block :data)]])]))))

(defn render []
  (reagent/render [app-view]
                  (js/document.getElementById "app")))

(defn ^:export run []
  (dispatch-sync [:set-data (cljs.reader/read-string (.-innerHTML (js/document.getElementById "data")))])
  (render))

