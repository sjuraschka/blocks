(ns blocks.client.core-dev
  (:require
    [re-frame.core :refer [reg-event-fx
                           dispatch]]
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

(fig/add-message-watch
  :edn-watcher
  (fn [{:keys [msg-name] :as msg}]
    (when (= msg-name :edn-files-changed)
      (dispatch [:fetch-data]))))

(defn ^:export reload
  []
  (core/render))

(defn ^:export run
  []
  (core/run))
