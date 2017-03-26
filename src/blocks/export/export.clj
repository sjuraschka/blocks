(ns blocks.export.export
  (:require
    [clojure.java.shell :refer [sh]]
    [me.raynes.fs :as fs]))

(defn export!
  "Exports assets for page"
  [page-config]
  (let [app-domain "http://localhost:6543/"
        page-domain (page-config :domain)
        page-path (page-config :url)
        asset-folders (page-config :assets)
        export-path "./export/"
        site-directory (str export-path page-domain)
        scrape-url (str app-domain page-domain page-path)]

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
              (str site-directory "/js/blocks.min.js"))

    (println "EXP: Scraping page" scrape-url)
    (let [html (sh "phantomjs" "export.js" scrape-url)]
      (if (= 1 (:exit html))
        (println "EXP: Could not load site. Did you start the server?")
        (spit (str site-directory page-path "index.html") (:out html))))))
