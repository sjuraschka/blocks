(ns blocks.server.handler
  (:require
    [compojure.core :refer [defroutes GET routes]]
    [compojure.route :refer [not-found]]
    [hiccup.core :refer [html]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.not-modified :refer [wrap-not-modified]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.session.cookie :refer [cookie-store]]
    [ring.middleware.session :refer [wrap-session]]
    [blocks.server.data :refer [path->page add-block-data read-pages-edn]]
    [blocks.server.page :refer [html-for-page]]))

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
     :body (html-for-page (add-block-data page) :dev)}))

(defroutes api-routes
  (GET "/api/domains/:domain/pages/*" {{domain :domain url :*} :params}
    (when-let [page (path->page domain (str "/" url))]
      {:status 200
       :headers {"Content-Type" "application/edn"}
       :body (pr-str {:page (add-block-data page)})})))

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

