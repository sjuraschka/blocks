(ns blocks.export.dns
  (:require
    [clojure.data.json :as json]
    [org.httpkit.client :as http]
    [environ.core :refer [env]]))

(def api-base-url
  "https://api.cloudflare.com/client/v4/")

(defn domain->zone
  "Extracts host.tld from a fully-qualified domain, ie. removes subdomains"
  [domain]
  (last (re-find #"(.*\.)?(\w*\.\w*)" domain)))

(defn get-cloudflare-zone
  [domain]
  (println "DNS: Finding zone for" domain)
  (let [response (-> @(http/request
                        {:method :get
                         :url (str api-base-url "/zones")
                         :headers {"X-Auth-Email" (env :cloudflare-email)
                                   "X-Auth-Key" (env :cloudflare-key)
                                   "Content-Type" "application/json"}
                         :query-params {:name (domain->zone domain)
                                        :status "active"}})
                     :body
                     (json/read-str :key-fn keyword)
                     )]
    (if (not (response :success))
      (do (println "DNS: Unable to find zone")
          (println response))
      (-> response
          :result
          first))))

(defn get-cname-record
  [zone-id domain]
  (println "DNS: Finding CNAME record for" domain)
  (let [response (-> @(http/request
                        {:method :get
                         :url (str api-base-url "/zones/" zone-id "/dns_records")
                         :headers {"X-Auth-Email" (env :cloudflare-email)
                                   "X-Auth-Key" (env :cloudflare-key)
                                   "Content-Type" "application/json"}
                         :query-params {:type "CNAME"
                                        :name domain}})
                     :body
                     (json/read-str :key-fn keyword))]
    (if (not (response :success))
      (do (println "DNS: Unable to find CNAME")
          (println response))
      (-> response
          :result
          first))))

(defn create-cname-record!
  [zone-id public-domain hidden-domain]
  (println "DNS: Creating CNAME record: " public-domain "->" hidden-domain)
  (let [response (-> @(http/request
                        {:method :post
                         :url (str api-base-url "/zones/" zone-id "/dns_records")
                         :headers {"X-Auth-Email" (env :cloudflare-email)
                                   "X-Auth-Key" (env :cloudflare-key)
                                   "Content-Type" "application/json"}
                         :form-params {:type "CNAME"
                                       :name public-domain
                                       :content hidden-domain}})
                     :body
                     (json/read-str :key-fn keyword))]
    (if (not (response :success))
      (do (println "Unable to create CNAME record")
          (println response))
      (-> response
          :result))))

(defn update-cname-record!
  [zone-id record-id public-domain hidden-domain]
  (println "DNS: Updating CNAME record:" public-domain "->" hidden-domain)
  (let [response (-> @(http/request
                        {:method :put
                         :url (str api-base-url "/zones/" zone-id "/dns_records/" record-id)
                         :headers {"X-Auth-Email" (env :cloudflare-email)
                                   "X-Auth-Key" (env :cloudflare-key)
                                   "Content-Type" "application/json"}
                         :body (json/write-str
                                 {:type "CNAME"
                                  :name public-domain
                                  :content hidden-domain})})
                     :body
                     (json/read-str :key-fn keyword))]
    (if (not (response :success))
      (do (println "DNS: Unable to update CNAME record")
          (println response))
      (-> response
          :result))))

(defn upsert-cname-record!
  "Creates CNAME at cloudflare"
  [public-domain hidden-domain]
  (let [zone (get-cloudflare-zone public-domain)]
    (if-let [record (get-cname-record (:id zone) public-domain)]
      (if (= hidden-domain (:content record))
        (println "DNS: CNAME record already set")
        (update-cname-record! (:id zone) (:id record) public-domain hidden-domain))
      (create-cname-record! (:id zone) public-domain hidden-domain))))

