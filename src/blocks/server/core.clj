(ns blocks.server.core
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [blocks.server.figwheel :refer [figwheel-server file-watcher]]
            [blocks.server.server :refer [server-component]]))


(def system
  (atom
    (component/system-map
      :http-server (component/using
                     (server-component)
                     [:port])
      :figwheel-server (figwheel-server)
      :file-watcher (component/using
                      (file-watcher)
                      [:figwheel-server]))))

(defn start! [port]
  (swap! system (fn [s] (-> s
                            (assoc :port port)
                            component/start))))

(defn stop! []
  (swap! system component/stop))

(defn reload! []
  (stop!)
  (start!))

(defn -main  [& args]
  (let [port (Integer/parseInt (first args))]
    (start! port)))

