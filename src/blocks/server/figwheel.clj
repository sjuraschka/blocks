(ns blocks.server.figwheel
  (:require [com.stuartsierra.component :as component]
            [figwheel-sidecar.components.figwheel-server :as server]
            [figwheel-sidecar.components.file-system-watcher :as fsw]
            [figwheel-sidecar.system :as sys]
            [figwheel-sidecar.utils :as utils]))

(def figwheel-config
  {:figwheel-options {:server-port 3434}
   :build-ids ["dev"]
   :all-builds
   [{:id "dev"
     :figwheel {:on-jsload "blocks.client.core/reload"}
     :source-paths ["src/blocks/client"]
     :compiler {:main 'blocks.client.core
                :asset-path "/js/dev"
                :output-to "resources/public/js/blocks.js"
                :output-dir "resources/public/js/dev"
                :verbose true}}]})

(defn make-file [path]
  {:file (utils/remove-root-path path)
   :type :edn})

(defn send-files [figwheel-server files]
  (server/send-message figwheel-server
                       ::server/broadcast
                       {:msg-name :edn-files-changed
                        :files files}))

(defn handle-notification [watcher files]
  (when-let [changed-files (->> files
                               (map str)
                               (filter #(.endsWith % ".edn"))
                               not-empty)]
    (let [figwheel-server (:figwheel-server watcher)
          sendable-files (map make-file changed-files)]
      (send-files figwheel-server sendable-files)
      (doseq [f sendable-files]
        (println "sending changed EDN file:" (:file f))))))


(defn figwheel-server []
  (sys/figwheel-system figwheel-config))

(defn file-watcher []
  (fsw/file-system-watcher {:watcher-name "EDN Watcher"
                            :watch-paths ["resources/data"]
                            :notification-handler handle-notification}))
