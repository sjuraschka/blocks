(ns blocks.server.server
  (:require 
    [com.stuartsierra.component :as component]
    [org.httpkit.server :refer [run-server]]
    [blocks.server.handler :refer [dev-app release-app]]))

(defrecord DevServer [stop-fn port]
  component/Lifecycle

  (start [component]
    (println "Starting HTTP Server")
    (assoc component :stop-fn (run-server #'dev-app {:port port})))

  (stop [component]
    (println "Stopping HTTP Server")
    (stop-fn :timeout 100)
    component
    (assoc component :stop-fn nil)))

(defn dev-server-component []
  (map->DevServer {}))


(defrecord ReleaseServer [stop-fn port]
  component/Lifecycle

  (start [component]
    (println "Starting HTTP Server")
    (assoc component :stop-fn (run-server #'release-app {:port port})))

  (stop [component]
    (println "Stopping HTTP Server")
    (stop-fn :timeout 100)
    component
    (assoc component :stop-fn nil)))

(defn release-server-component []
  (map->ReleaseServer {}))
