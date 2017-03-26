(ns blocks.server.data
  (:require
    [clojure.java.io :as io]))

(defn read-pages-edn []
  (read-string (slurp (io/resource "data/pages.edn"))))

(defn read-blocks-edn []
  (->> (io/resource "data/blocks")
       io/file
       file-seq
       (filter (fn [f]
                 (.isFile f)))
       (map slurp)
       (map read-string)
       (apply merge)))

(defn add-block-data [page]
  (let [blocks (read-blocks-edn)]
    (-> page
        (update-in [:blocks]
                   (fn [bs]
                     (vec (map (fn [block-id]
                                 (blocks block-id)) bs)))))))

(defn path->page [domain url]
  (->> (read-pages-edn)
       (filter (fn [page]
                 (and
                   (= (page :url) url)
                   (= (page :domain) domain))))
       first))
