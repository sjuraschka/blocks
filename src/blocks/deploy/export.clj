(ns blocks.deploy.export
  (:require
    [clojure.java.shell :refer [sh]]
    [me.raynes.fs :as fs]
    [blocks.deploy.server :as server]
    [blocks.server.crypto :refer [sha256-file]]))

(defn dir-digest 
  "Generates a digest for a directory, by joining sha256-digests of all contained files"
  [directory]
  (->> (file-seq (fs/file directory))
       (filter fs/file?)
       (map sha256-file)
       (apply str)))

(defn maybe-compile-cljs! 
  "Runs cljsbuild if necessary"
  []
  (let [digest-path "resources/public/js/prod/.cljs-export-digest"
        watch-path "src/blocks/client"]
    (if (and 
          (fs/exists? "resources/public/js/blocks.min.js")
          (fs/exists? digest-path)
          (= (slurp digest-path) (dir-digest watch-path)))
      (println "EXP: Reusing existing blocks.min.js")
      (do
        (println "EXP: Compiling blocks.min.js (this can take a while...)")
        (fs/delete "resources/public/js/blocks.min.js")
        (fs/delete-dir "resources/public/js/prod/")
        (sh "lein" "cljsbuild" "once" "prod")
        (spit digest-path (dir-digest watch-path))))))

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

    (maybe-compile-cljs!)

    (prepare-assets! {:site-directory site-directory
                      :asset-folders (page-config :assets)
                      :page-path page-path})

    (scrape! {:url-to-scrape (str app-domain page-domain page-path)
              :out-path (str site-directory page-path "index.html")})
    
    (println "EXP: Stopping server")
    (server/stop!)))
