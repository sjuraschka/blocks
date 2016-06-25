(ns blocks.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as reagent]
            [garden.core :as garden]
            [clojure.string :as string]
            [re-frame.core :refer [register-handler
                                   path
                                   register-sub
                                   dispatch
                                   dispatch-sync
                                   subscribe]]))

(defn nav-template [data]
  [:div.nav
   [:img.logo {:src (data :logo)} ]
   [:div.links
    (for [link (data :links)]
      [:a {:href (link :url)}
       (link :text)])]])

(defn hero-template [data]
  [:div.hero
   [:div.heading
    (data :heading)]])

(def blocks
  {"braid-nav"
   {:name "Braid Nav"
    :template "nav"
    :data {:logo "http://placehold.it/50x50"
           :links [{:text "For Teams"
                    :url "..."
                    :style "special"}
                   {:text "Pricing"
                    :url "..."}]}}
   "braid-hero"
   {:name "Braid Hero"
    :template "hero"
    :data {:heading "Braid YOOO"}}})

(def pages
  [{:domain "www.braidchat.com"
    :url "/lawyers"
    :meta {:title "Braid"
           :description "Braid is a..."
           :image "url()"}
    :colors ["#abcdef"
             "#000000"]
    :blocks ["braid-nav"
             "braid-hero"]}
   {:domain "www.braidchat.com"
    :url "/programmers"
    :meta {:title "Braid"
           :description "Braid is a..."
           :image "url()"}
    :colors ["#abcdef"
             "#000000"]
    :blocks ["braid-nav"
             "braid-hero"]}])

(defn app-view []
  [:div
   (for [block-id (get-in pages [0 :blocks])]
     (let [block (blocks block-id)]
       [:div
        (case (block :template)
          "nav" [nav-template (block :data)]
          "hero" [hero-template (block :data)])]))])

(defn ^:export run
  []
  (reagent/render [app-view]
                  (js/document.getElementById "app")))

(defn ^:export reload
  []
  (run))
