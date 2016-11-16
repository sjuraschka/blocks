(ns blocks.client.core-dev
  (:require
    [reagent.core :as reagent]
    [re-frame.core :refer [reg-event-fx
                           reg-sub
                           reg-event-db
                           dispatch-sync
                           dispatch
                           subscribe]]
    [figwheel.client :as fig]
    [ajax.core :refer [GET]]
    [blocks.client.core :as core]))

(enable-console-print!)

(reg-event-fx
  :fetch-data
  (fn [state _]
    (GET (str "/api/domains/" (get-in state [:page :domain])
              "/pages" (get-in state [:page :url]))
      {:handler (fn [data]
                  (println "Reloading EDN")
                  (println data)
                  (dispatch [:set-data (cljs.reader/read-string data)]))})
    {}))

(reg-event-db
  :set-data
  (fn [_ [_ data]]
    data))

(reg-sub
  :page
  (fn [state _]
    (state :page)))

(fig/add-message-watch
  :edn-watcher
  (fn [{:keys [msg-name] :as msg}]
    (when (= msg-name :edn-files-changed)
      (dispatch [:fetch-data]))))


(defn app-view []
  (let [page (subscribe [:page])]
    (fn []
      [core/page-view @page])))

(defn render []
  (reagent/render [app-view]
                  (js/document.getElementById "app")))

(defn ^:export reload
  []
  (render))

(defn ^:export run
  []
  (dispatch-sync [:set-data (core/get-data)])
  (render))
