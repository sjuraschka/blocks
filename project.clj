(defproject blocks "0.0.1"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.89"]
                 [re-frame "0.7.0"]
                 [garden "1.3.2"]]

  :plugins [[lein-figwheel "0.5.4-4"]]

  :figwheel {:server-port 3434}

  :cljsbuild {:builds
              [{:id "dev"
                :figwheel true
                :source-paths ["src"]
                :compiler {:main blocks.core
                           :asset-path "/js/dev"
                           :output-to "resources/public/js/dev.js"
                           :output-dir "resources/public/js/dev"
                           :verbose true}}]})
