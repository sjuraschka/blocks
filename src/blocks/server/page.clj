(ns blocks.server.page
  (:require
    [hiccup.core :refer [html]]
    [blocks.server.crypto :refer [sha256-file]]))

(defn html-for-page [page mode] 
  (html
    [:html
     [:head
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "x-ua-compatible" :content "ie=edge"}]
      [:title (get-in page [:meta :title])]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
      [:meta {:name "generator" :content "blocks"}]
      (when (get-in page [:meta :description])
        [:meta {:name "description" :content (get-in page [:meta :description])}])
      (when (get-in page [:meta :image])
        [:meta {:name "image" :content (get-in page [:meta :image])}])
      [:style {:type "text/css"}
       (slurp (clojure.java.io/resource "public/css/reset.css"))]
      (when-let [favicon (get-in page [:favicon])]
        [:link {:rel "icon" :type "image/png" :href favicon}])]
     [:body
      [:div {:id "app"}]

      [:script {:type "text/edn" :id "data"}
       (pr-str {:page page})]

      (when (= :dev mode)
        [:script {:src "/js/blocks.js" :type "text/javascript"}])

      (when (= :dev mode)
        [:script {:type "text/javascript"}
         "blocks.client.core_dev.run()"])

      (when (= :prod mode)
        (let [digest (sha256-file (clojure.java.io/resource "public/js/blocks.min.js"))]
          [:script {:type "text/javascript"
                    :src (str "/js/blocks.min.js?v=" digest)
                    :integrity (str "sha256-" digest) 
                    :crossorigin "anonymous"}]))

      (when (= :prod mode)
        [:script {:type "text/javascript"}
         "blocks.client.core.run()"])]]))
