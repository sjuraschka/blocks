(ns blocks.server.server
  (:require [com.stuartsierra.component :as component]
            [org.httpkit.server :refer [run-server]]
            [blocks.server.handler :refer [app]]))

(defrecord Server [stop-fn port]
  component/Lifecycle

  (start [component]
    (println "Starting HTTP Server")
    (assoc component :stop-fn (run-server #'app {:port port})))

  (stop [component]
    (println "Stopping HTTP Server")
    (stop-fn :timeout 100)
    component
    (assoc component :stop-fn nil)))

(defn server-component []
  (map->Server {}))
