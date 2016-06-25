(ns blocks.server.handler
  (:require
    [hiccup.core :refer [html]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.not-modified :refer [wrap-not-modified]]
    [ring.middleware.content-type :refer [wrap-content-type]]))

(def blocks
  {"fetch-nav"
   {:template "nav"
    :data {:logo "http://placehold.it/50x50"
           :links [{:text "For Teams"
                    :url "..."
                    :style "special"}
                   {:text "Pricing"
                    :url "..."}]}}
   "braid-nav"
   {:template "nav"
    :data {:logo "http://placehold.it/50x50"
           :links [{:text "For Teams"
                    :url "..."
                    :style "special"}
                   {:text "Pricing"
                    :url "..."}]}}
   "fetch-hero"
   {:template "hero"
    :data {:heading "Fetch!"}}
   "braid-hero"
   {:template "hero"
    :data {:heading "Braid!"}}})

(def pages
  [{:domain "www.usefetch.com"
    :url "/"
    :meta {:title "Fetch"
           :description "Fetch is a..."
           :image "url()"}
    :colors ["#abcdef"
             "#000000"]
    :blocks ["fetch-nav"
             "fetch-hero"]}
   {:domain "www.braidchat.com"
    :url "/"
    :meta {:title "Braid"
           :description "Braid is a..."
           :image "url()"}
    :colors ["#abcdef"
             "#000000"]
    :blocks ["braid-nav"
             "braid-hero"]}])

(defn page-handler [request]
  (if-let [page (->> pages
                     (filter (fn [page]
                               (and
                                 (= (page :url) (request :uri))
                                 (= (page :domain) (request :server-name)))))
                     first)]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (html
             [:html
              [:head
               [:title (get-in page [:meta :title])]]
              [:body
               [:div {:id "app"}]
               [:script {:type "text/edn" :id "data"}
                (pr-str {:page (-> page
                                   (update-in [:blocks]
                                              (fn [bs]
                                                (vec (map (fn [block-id]
                                                            (blocks block-id)) bs)))))})]
               [:script {:src "/js/dev.js" :type "text/javascript"}]
               [:script {:type "text/javascript"}
                "blocks.client.core.run()"]]])}
    {:status 404
     :body "404; Thank you visitor! But our page is in another castle!"}))

(def app
  (-> page-handler
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))
