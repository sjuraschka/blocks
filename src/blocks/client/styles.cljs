(ns blocks.client.styles
  (:require
    [clojure.string :as string]
    [garden.core :refer [css]]
    [garden.stylesheet :refer [at-import at-font-face at-keyframes]]))

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

(def fa-at-font-face
  (let [version "4.7.0"
        fa-cdn-url (str "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/" version)]
    (at-font-face
      {:font-family "FontAwesome"
       :src [(str "local('FontAwesome')")
             (str "url('" fa-cdn-url "/fonts/fontawesome-webfont.eot?#iefix&v=" version "') format('embedded-opentype')")
             (str "url('" fa-cdn-url "/fonts/fontawesome-webfont.woff2?v=" version "') format('woff2')")
             (str "url('" fa-cdn-url "/fonts/fontawesome-webfont.woff?v=" version "') format('woff')")
             (str "url('" fa-cdn-url "/fonts/fontawesome-webfont.ttf?v=" version "') format('truetype')")
             (str "url('" fa-cdn-url "/fonts/fontawesome-webfont.svg?v=" version "#fontawesomeregular') format('svg')")]
       :font-weight "normal"
       :font-style "normal"})))

(defn page-styles [data]
  [(google-fonts-import data)

   (at-keyframes :anim-spin
     ["0%" {:transform "rotate(0deg)"}]
     ["100%" {:transform "rotate(359deg)"}])

   fa-at-font-face

   [:body :input
    {:font-family [(str "\"" (get-in data [:styles :fonts :body :name]) "\"") "sans-serif"]
     :font-weight (get-in data [:styles :fonts :body :weight])
     :line-height "1.25"}]

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
