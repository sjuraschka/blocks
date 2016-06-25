(ns blocks.server.core
  (:gen-class)
  (:require [org.httpkit.server :refer [run-server]]
            [blocks.server.handler :refer [app]]))

(defonce server (atom nil))

(defn stop-server!
  []
  (when-let [stop-fn @server]
    (stop-fn :timeout 100)))

(defn start-server!
  [port]
  (stop-server!)
  (reset! server (run-server #'app {:port port})))

(defn -main  [& args]
  (let [port (Integer/parseInt (first args))]
    (start-server! port)))
