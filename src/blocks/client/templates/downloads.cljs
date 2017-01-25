(ns blocks.client.templates.downloads
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.downloads

   ])

(defn component [data]
  [:section.downloads

   ])


(defmethod template "downloads" [_]
  {:css styles
   :component component})
