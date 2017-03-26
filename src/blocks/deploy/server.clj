(ns blocks.deploy.server
  (:require
    [compojure.core :refer [defroutes GET]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [mount.core :as mount :refer [defstate]]
    [org.httpkit.server :refer [run-server]]
    [blocks.server.data :refer [path->page add-block-data]]
    [blocks.server.page :refer [html-for-page]]))

(defroutes export-routes
  (GET ["/:domain/*"] {{domain :domain url :*} :params}
    (when-let [page (path->page domain (str "/" url))]
      {:status 200
       :headers {"Content-Type" "text/html"}
       :body (html-for-page (add-block-data page) :prod)})))

(def export-app
  (-> export-routes
      (wrap-resource "public")
      (wrap-resource "data/assets")
      (wrap-content-type)))

(def port 6543)

(defstate export-server 
  :start (run-server #'export-app {:port port})
  :stop (export-server))

(defn start! []
  (mount/start #'export-server))

(defn stop! []
  (mount/stop))
