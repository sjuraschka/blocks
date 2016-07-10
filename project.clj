(defproject blocks "0.0.1"
  :dependencies [[org.clojure/clojure "1.8.0"]

                 ; server-side
                 [figwheel-sidecar "0.5.4-4"]
                 [com.stuartsierra/component "0.3.1"]
                 [http-kit "2.1.19"]
                 [ring/ring-devel "1.4.0"]
                 [hiccup "1.0.5"]
                 [compojure "1.5.1"]

                 ; export
                 [amazonica "0.3.64"]
                 [environ "1.0.3"]

                 ; client-side
                 [org.clojure/clojurescript "1.9.89"]
                 [re-frame "0.7.0"]
                 [garden "1.3.2"]
                 [cljs-ajax "0.5.8"]]

  :source-paths ["src"]

  :main blocks.server.core

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-environ "1.0.3"]]

  :cljsbuild {:builds
              [{:id "prod"
                :source-paths ["src/blocks/client"]
                :compiler {:main blocks.client.core
                           :output-to "resources/public/js/blocks.js"
                           :output-dir "resources/public/js/prod"
                           :optimizations :advanced
                           :verbose true}}]})
