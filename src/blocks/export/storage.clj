(ns blocks.export.storage
  (:require
    [clojure.string :as string]
    [clojure.java.shell :refer [sh]]
    [clojure.data.json :as json]
    [environ.core :refer [env]]
    [org.httpkit.client :as http]))

(defn domain->zone [domain]
  (string/replace domain #"\." "-"))

; storage schema:
#_{:cdn_resources []
   :credentials {:host "..."
                 :pass "..."
                 :protocol "FTP, SFTP"
                 :user "..."}
   :id "..."
   :storage_location_id "..."
   :used_space "..."
   :zone_name "..."}

(defn get-storage
  [domain]
  (println "STR: Getting storage for" domain)
  (let [response (-> @(http/request
                        {:method :get
                         :url "https://api.cdn77.com/v2.0/storage/list"
                         :query-params {:login (env :cdn77-login)
                                        :passwd (env :cdn77-password)}})
                     :body
                     (json/read-str :key-fn keyword))]
    (if (= "error" (response :status))
      (do (println "STR: Did not find storage-id")
          (println response))
      (->> response
           :storages
           (filter (fn [storage]
                     (= (storage :zone_name) (domain->zone domain))))
           first))))

(defn create-storage!
  "Creates storage at CDN77"
  [domain]
  (println "STR: Creating storage for" domain)
  (let [response (-> @(http/request {:method :post
                                     :url "https://api.cdn77.com/v2.0/storage/create"
                                     :form-params {:login (env :cdn77-login)
                                                   :passwd (env :cdn77-password)
                                                   :zone_name (domain->zone domain)
                                                   :storage_location_id (env :cdn77-storage-location)}})
                     :body
                     (json/read-str :key-fn keyword))]
    (if (= "error" (response :status))
      (do (println "STR: Could not create storage")
          (println response))
      (->> response
           :storage))))

(defn upload!
  "Uploads files to CDN77 storage; returns list of updated files"
  [domain storage]
  (println "STR: Uploading files for" domain)
  (let [directory (str "./export/" domain "/")
        user (get-in storage [:credentials :user])
        password (get-in storage [:credentials :pass])
        host (get-in storage [:credentials :host])
        domain (env :cdn77-storage-location)]
    (let [result (sh "rsync"
                     "-aic"
                     "--delete"
                     "--delete-excluded"
                     "--exclude" "*.DS_Store"
                     "-e" (str "sshpass -p " password " ssh -o PubkeyAuthentication=no -l " user )
                     directory
                     (str user "@" host ":/www/"))]
      (println result)
      (-> result
          :out
          (string/split #"\n")
          (->> (map (fn [f]
                      (last (re-matches #"<.* (.*)" f))))
               (remove nil?))))))