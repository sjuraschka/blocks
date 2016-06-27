(ns blocks.server.upload
  (:require [environ.core :refer [env]]
            [amazonica.aws.s3 :as s3]
            [amazonica.aws.s3transfer :as s3-tx]))

(def s3-creds {:access-key (env :s3-id)
               :secret-key (env :s3-secret)
               :endpoint (env :s3-endpoint)})

(defn s3-upload!
  "given a file, uploads it to s3 as path"
  [file path]
  (println "upload" path)
  (s3/put-object s3-creds
                 :bucket-name (env :s3-bucket)
                 :key path
                 :file file))

(defn upload!
  "process and upload all fiels"
  []
  (let [base-path (clojure.java.io/file "export")
        files (->> base-path
                   file-seq
                   (filter (fn [f] (.isFile f))))]
    (doseq [file files]
      (let [path (-> (.toURI base-path)
                     (.relativize (.toURI file))
                     .getPath)]
        (s3-upload! file path)))))
