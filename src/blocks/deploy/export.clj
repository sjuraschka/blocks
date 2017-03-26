(ns blocks.deploy.export
  (:require
    [clojure.java.shell :refer [sh]]
    [me.raynes.fs :as fs]
    [blocks.deploy.server :as server]))

(defn prepare-assets! 
  [{:keys [site-directory asset-folders page-path]}]

  (when (fs/exists? site-directory)
    (println "EXP: Removing " site-directory)
    (fs/delete-dir site-directory))

  (println "EXP: Creating export directory" site-directory)
  (fs/mkdirs (str site-directory page-path))

  (doseq [asset-folder asset-folders]
    (println "EXP: Copying asset folder" asset-folder)
    (fs/copy-dir (str "./resources/data/assets/" asset-folder)
                 (str site-directory "/" asset-folder)))

  (println "EXP: Copying blocks.min.js")
  (fs/copy+ "./resources/public/js/blocks.min.js"
            (str site-directory "/js/blocks.min.js")))

(defn scrape! 
  [{:keys [url-to-scrape out-path]}]
  (println "EXP: Scraping page" url-to-scrape)
  (let [html (sh "phantomjs" "export.js" url-to-scrape)]
    (if (= 1 (:exit html))
      (println "EXP: Could not load site. Did you start the server?")
      (spit out-path (:out html)))))

(defn export!
  "Exports assets for page"
  [page-config]
  (let [app-domain (str "http://localhost:" server/port "/")
        page-domain (page-config :domain)
        page-path (page-config :url)
        export-path "./export/"
        site-directory (str export-path page-domain)]

    (println "EXP: Starting server")
    (server/start!)

    (prepare-assets! {:site-directory site-directory
                      :asset-folders (page-config :assets)
                      :page-path page-path})

    (scrape! {:url-to-scrape (str app-domain page-domain page-path)
              :out-path (str site-directory page-path "index.html")})
    
    (println "EXP: Stopping server")
    (server/stop!)))
