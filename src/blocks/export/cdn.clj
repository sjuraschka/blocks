(ns blocks.export.cdn
  (:require
    [org.httpkit.client :as http]
    [clojure.data.json :as json]
    [environ.core :refer [env]]))

(def api-base-url
  "https://api.cdn77.com/v2.0")

; cdn schema
#_{:cache_expiry 17280
   :cdn_url "..."
   :cname "..."
   :hlp_deny_empty_referer 0
   :hlp_referer_domains []
   :hlp_type nil
   :http2 1
   :https_redirect_code nil
   :id 95353
   :ignored_query_params []
   :label "..."
   :mp4_pseudo_on 0
   :origin_scheme "..."
   :origin_url ""
   :other_cnames []
   :platform "nxg"
   :qs_status 1
   :setcookie_status 0
   :storage_id "..."
   :url_signing_key ""
   :url_signing_on 0}

(defn get-cdn [domain]
  (println "CDN: Retrieving CDN for" domain)
  (let [response (-> @(http/request
                        {:method :get
                         :url (str api-base-url "/cdn-resource/list")
                         :query-params {:login (env :cdn77-login)
                                        :passwd (env :cdn77-password)}})
                     :body
                     (json/read-str :key-fn keyword))]
    (if (= "error" (response :status))
      (do (println "CDN: Could not access cdn resources")
          (println response))
      (->> response
           :cdnResources
           (filter (fn [cdn]
                     (= (cdn :cname) domain)))
           first))))

(defn create-cdn! [domain storage]
  (println "CDN: Creating CDN for" domain)
  (let [response (-> @(http/request
                        {:method :post
                         :url (str api-base-url "/cdn-resource/create")
                         :form-params {:login (env :cdn77-login)
                                       :passwd (env :cdn77-password)
                                       :type "standard"
                                       :cname domain
                                       :label domain
                                       :storage_id (:id storage)
                                       :instant_ssl 1}})
                     :body
                     (json/read-str :key-fn keyword))]
    (if (= "error" (response :status))
      (do (println "CDN: Unable to create CDN")
          (println response))
      (-> response
          :cdnResource))))

(defn purge-files! [cdn-id file-paths]
  (println "CDN: Purging files " file-paths)
  (when (seq file-paths)
    (let [response (-> @(http/request
                          {:method :post
                           :url (str api-base-url "/data/purge")
                           :form-params {:login (env :cdn77-login)
                                         :passwd (env :cdn77-password)
                                         :cdn_id cdn-id
                                         "url[]" file-paths}})
                       :body
                       (json/read-str :key-fn keyword))]
      (if (= "error" (response :status))
        (do (println "CDN: Unable to purge file")
            (println response))
        (-> response)))))

(defn prefetch-files! [cdn-id file-paths]
  (println "CDN: Prefetching files " file-paths)
  (when (seq file-paths)
    (let [response (-> @(http/request
                          {:method :post
                           :url (str api-base-url "/data/prefetch")
                           :form-params {:login (env :cdn77-login)
                                         :passwd (env :cdn77-password)
                                         :cdn_id cdn-id
                                         "url[]" file-paths}})
                       :body
                       (json/read-str :key-fn keyword))]
      (if (= "error" (response :status))
        (do (println "CDN: Unable to prefetch files")
            (println response))
        (-> response)))))
