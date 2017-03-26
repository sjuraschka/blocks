(ns blocks.server.core
  (:require 
    [com.stuartsierra.component :as component]
    [blocks.server.figwheel :refer [figwheel-server file-watcher]]
    [blocks.server.server :refer [dev-server-component]]))

(def dev-system
  (atom
    (component/system-map
      :http-server (component/using
                     (dev-server-component)
                     [:port])
      :figwheel-server (figwheel-server)
      :file-watcher (component/using
                      (file-watcher)
                      [:figwheel-server]))))

(defn start-dev! [port]
  (swap! dev-system (fn [s] (-> s
                                (assoc :port port)
                                component/start))))


