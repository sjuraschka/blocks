(ns blocks.export.storage
  (:require
    [environ.core :refer [env]]
    [me.raynes.fs :as fs]
    [aws.sdk.s3 :as s3])
  (:import
    [java.nio.file Paths]
    [java.net URI]))

(defn relative-path [path-1 path-2]
  (let [path-1 (Paths/get (URI. (str "file://" (.getPath (fs/file path-1)))))
        path-2 (Paths/get (URI. (str "file://" (.getPath (fs/file path-2)))))]
    (.toString (.relativize path-1 path-2))))

(defn upload!
  "Uploads files to CDN77 storage; returns list of updated files"
  [{:keys [directory domain]}]
  (println "STR: Uploading files for" domain)
  (let [files (->> (file-seq (fs/file directory))
                   (filter fs/file?))]
    (doseq [f files]
      (let [remote-path (str domain "/" (relative-path directory f))]
        (println "STR:\tUploading " remote-path)
        (s3/put-object {:access-key (env :s3-id)
                        :secret-key (env :s3-secret)} 
                       (env :s3-bucket) 
                       remote-path
                       f)))
    (for [f files]
      (relative-path directory f))))



