(ns blocks.server.handler
  (:require
    [clojure.string :as string]
    [compojure.core :refer [defroutes GET routes]]
    [compojure.route :refer [not-found]]
    [hiccup.core :refer [html]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.not-modified :refer [wrap-not-modified]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.session.cookie :refer [cookie-store]]
    [ring.middleware.session :refer [wrap-session]]
    [blocks.server.crypto :refer [sha256-file]]))

(defn read-pages-edn []
  (read-string (slurp (clojure.java.io/resource "data/pages.edn"))))

(defn read-blocks-edn []
  (->> (clojure.java.io/resource "data/blocks")
       clojure.java.io/file
       file-seq
       (filter (fn [f]
                 (.isFile f)))
       (map slurp)
       (map read-string)
       (apply merge)))

(defn get-page-data [page]
  (let [blocks (read-blocks-edn)]
    {:page (-> page
               (update-in [:blocks]
                          (fn [bs]
                            (vec (map (fn [block-id]
                                        (blocks block-id)) bs)))))}))

(defn path->page [domain url]
  (let [pages (read-pages-edn)]
    (when-let [page (->> pages
                         (filter (fn [page]
                                   (and
                                     (= (page :url) url)
                                     (= (page :domain) domain))))
                         first)]
      page)))

(defn strip-slash
  [s]
  (if (string/ends-with? s "/")
    (apply str (butlast s))
    s))

(defn index-page-response []
  (let [domains (->> (read-pages-edn)
                     (group-by :domain))]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (html
             [:html
              [:body
               (for [[domain pages] domains]
                 [:ul
                  (for [page pages]
                    [:li
                     [:a {:href (str "/pages/" (page :domain) (page :url))}
                      (str (page :domain) (page :url))]])])]])}))

(defn dev-page-response [domain url]
  (when-let [page (path->page domain (str "/" url))]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (html
             [:html
              [:head
               [:meta {:charset "utf-8"}]
               [:meta {:http-equiv "x-ua-compatible" :content "ie=edge"}]
               [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
               [:meta {:name "generator" :content "blocks"}]
               [:style {:type "text/css"}
                (slurp (clojure.java.io/resource "public/css/reset.css"))]
               (when-let [favicon (get-in page [:favicon])]
                 [:link {:rel "icon" :type "image/png" :href favicon}])
               [:title (get-in page [:meta :title])]]
              [:body
               [:div {:id "app"}]
               [:script {:type "text/edn" :id "data"}
                (pr-str (get-page-data page))]
               [:script {:src "/js/blocks.js" :type "text/javascript"}]
               [:script {:type "text/javascript"}
                "blocks.client.core_dev.run()"]]])}))

(defroutes api-routes
  (GET "/api/domains/:domain/pages/*" {{domain :domain url :*} :params}
    (when-let [page (path->page domain (str "/" url))]
      {:status 200
       :headers {"Content-Type" "application/edn"}
       :body (pr-str (get-page-data page))})))

(defroutes dev-routes
  (GET "/" request
    (let [domain (get-in request [:session :domain])
          url ""]
      (if (not domain)
        (index-page-response)
        (if-let [page (dev-page-response domain url)]
          page
          (index-page-response)))))

  (GET "/*" request
    (let [domain (get-in request [:session :domain])
          url (get-in request [:params :*])]
      (dev-page-response domain url)))

  (GET "/pages/:domain/*" request
    {:session {:domain (get-in request [:params :domain])}
     :status 302
     :headers {"Location" (str "/" (get-in request [:params :*]))}})

  (GET "/pages" _
    (index-page-response)))


(defn file-integrity [path]
  (str "sha256-" (sha256-file path)))

(defroutes release-routes
  (GET ["/:domain/*"] {{domain :domain url :*} :params}
    (when-let [page (path->page domain (str "/" url))]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (html
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
                  (pr-str (get-page-data page))]
                 [:script {:type "text/javascript"
                           :src (str "/js/blocks.min.js"
                                     "?cb="
                                     (file-integrity (clojure.java.io/resource "public/js/blocks.min.js")))
                           :integrity (file-integrity (clojure.java.io/resource "public/js/blocks.min.js"))
                           :crossorigin "anonymous"}]
                 [:script {:type "text/javascript"}
                  "blocks.client.core.run()"]]])})))

(def dev-app
  (-> (routes
        dev-routes
        api-routes
        (not-found "Page not found."))
      (wrap-session {:store (cookie-store {:key "abcdefghijklmnop"})
                     :cookie-name "blocks-dev"})
      (wrap-resource "public")
      (wrap-resource "data/assets")
      (wrap-content-type)
      (wrap-not-modified)))

(def release-app
  (-> (routes
        release-routes)
      (wrap-resource "public")
      (wrap-resource "data/assets")
      (wrap-content-type)))
