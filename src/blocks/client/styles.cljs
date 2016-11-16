(ns blocks.client.styles
  (:require
    [clojure.string :as string]
    [garden.core :refer [css]]
    [garden.stylesheet :refer [at-import]]))

(defn google-fonts-import [data]
   (when-let [google-fonts (->> (get-in data [:styles :fonts])
                                vals
                                (filter (fn [font]
                                          (= "google" (font :source))))
                                not-empty)]
     (at-import (str "//fonts.googleapis.com/css?family="
                     (->> google-fonts
                          (map (fn [font]
                                 (str (string/replace (font :name) #" " "+")
                                      ":" (font :weight))))
                          (string/join "|"))))))


(defn page-styles [data]
  [(at-import "//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css")

   (google-fonts-import data)

   [:body :input
    {:font-family [(str "\"" (get-in data [:styles :fonts :body :name]) "\"") "sans-serif"]
     :font-weight (get-in data [:styles :fonts :body :weight])
     :line-height "1.25" }]

   [:h1 :h2 :h3 :h4 :h5
    {:font-family [(str "\"" (get-in data [:styles :fonts :headings :name]) "\"") "sans-serif"]
     :font-weight (get-in data [:styles :fonts :headings :weight])}]

   [:.block
    {:overflow "hidden"}

    [:.content
     {:max-width "1000px"
      :position "relative"
      :margin "0 auto"}]]])

(defn styles-view [styles]
  [:style {:type "text/css"
           :dangerouslySetInnerHTML
           {:__html (css
                      {:auto-prefix #{:transition
                                      :flex-direction
                                      :flex-shrink
                                      :align-items
                                      :animation
                                      :flex-grow}
                       :vendors ["webkit" "moz"]}
                      styles)}}])
