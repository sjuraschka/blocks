(ns blocks.export.core
  (:require
    [clojure.string :as string]
    [blocks.export.export :as export]
    [blocks.export.cdn :as cdn]
    [blocks.export.storage :as storage]
    [blocks.export.dns :as dns]))

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
        domain (page-config :domain)
        storage (or (storage/get-storage domain)
                    (storage/create-storage! domain))
        files-changed (storage/upload! domain storage)
        cdn (or (cdn/get-cdn domain)
                (cdn/create-cdn! domain storage))
        _ (dns/upsert-cname-record! domain (cdn :cdn_url))
        paths-changed (->> files-changed
                           (map (fn [f]
                                  (str "/" f)))
                           (map (fn [f]
                                  (string/replace f "index.html" ""))))
        _ (cdn/purge-files! (:id cdn) paths-changed)
        _ (cdn/prefetch-files! (:id cdn) paths-changed)]
    (println "Released " domain url "successfully!")))
