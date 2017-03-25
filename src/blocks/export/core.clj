(ns blocks.export.core
  (:require
    [clojure.string :as string]
    [blocks.export.export :as export]
    [blocks.export.cdn :as cdn]
    [blocks.export.storage :as storage]
    [blocks.export.dns :as dns]
    [environ.core :refer [env]]))

(defn read-pages-edn []
  (read-string (slurp (clojure.java.io/resource "data/pages.edn"))))

(defn get-page-config [domain url]
  (->> (read-pages-edn)
       (filter (fn [page]
                 (and
                   (= (page :domain) domain)
                   (= (page :url) url))))
       first))

(defn release! [domain url]
  (let [page-config (get-page-config domain url)
        _ (export/export! page-config)
        files-changed (storage/upload! {:directory (str "./export/" domain "/")
                                        :domain domain})
        origin (str (env :s3-bucket) ".s3-website-us-east-1.amazonaws.com/" domain "/")
        cdn (or (cdn/get-cdn {:domain domain})
                (cdn/create-cdn! {:domain domain
                                  :origin-url origin}))
        _ (dns/upsert-cname-record! domain (cdn :cdn_url))
        paths-changed (->> files-changed
                           (map (fn [f]
                                  (str "/" f)))
                           (map (fn [f]
                                  (string/replace f "index.html" ""))))
        _ (cdn/purge-files! {:cdn-id (:id cdn)
                             :file-paths paths-changed})
        _ (cdn/prefetch-files! {:cdn-id (:id cdn)
                                :file-paths paths-changed})]
    (println "Released " domain url "successfully!")))
