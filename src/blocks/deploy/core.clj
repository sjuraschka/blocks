(ns blocks.deploy.core
  (:require
    [clojure.string :as string]
    [environ.core :refer [env]]
    [blocks.deploy.cdn :as cdn]
    [blocks.deploy.dns :as dns]
    [blocks.deploy.export :as export]
    [blocks.deploy.storage :as storage]
    [blocks.server.data :refer [read-pages-edn]]))

(defn deploy! [domain]
  (println "DEP: Deploying" domain)
  (let [pages (->> (read-pages-edn)
                   (filter (fn [page]
                             (= (page :domain) domain))))

        _ (export/export! domain pages)

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
    
    (println "DEP: Deployed" domain "successfully!\n")))
