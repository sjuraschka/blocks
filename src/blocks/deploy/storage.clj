(ns blocks.deploy.storage
  (:require
    [aws.sdk.s3 :as s3]
    [digest :as digest]
    [environ.core :refer [env]]
    [me.raynes.fs :as fs])
  (:import
    [java.nio.file Paths]
    [java.net URI]))

(defn remote-files-md5 [domain]
  (->> (s3/list-objects 
        {:access-key (env :s3-id)
         :secret-key (env :s3-secret)}
        (env :s3-bucket)
        {:prefix domain})
      :objects
      (reduce 
        (fn [memo object]
          (assoc memo (object :key) (get-in object [:metadata :etag]))) {})))

(defn relative-path [path-1 path-2]
  (let [path-1 (Paths/get (URI. (str "file://" (.getPath (fs/file path-1)))))
        path-2 (Paths/get (URI. (str "file://" (.getPath (fs/file path-2)))))]
    (.toString (.relativize path-1 path-2))))

(defn upload!
  "Uploads files to CDN77 storage; returns list of updated files"
  [{:keys [directory domain]}]
  (println "STR: Uploading files for" domain)
  (let [files (->> (file-seq (fs/file directory))
                   (filter fs/file?))
        remote-files-md5 (remote-files-md5 domain)
        uploaded-files (atom [])]
    (doseq [f files]
      (let [rel-path (relative-path "./export/" f)]
        (if (= (digest/md5 f)
               (remote-files-md5 rel-path))
          (println "STR:\t Skipping " rel-path)
          (do 
            (println "STR:\tUploading " rel-path)
            (s3/put-object {:access-key (env :s3-id)
                            :secret-key (env :s3-secret)} 
                           (env :s3-bucket) 
                           rel-path
                           f)
            (swap! uploaded-files conj (relative-path directory f))))))
    @uploaded-files))



