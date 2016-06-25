(ns blocks.client.core
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

(register-handler
  :initialize
  (fn [state _]
    (cljs.reader/read-string (.-innerHTML (js/document.getElementById "data")))))

(register-sub
  :page
  (fn [state _]
    (reaction (@state :page))))

(enable-console-print!)

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

(defn app-view []
  (let [page (subscribe [:page])]
    [:div
     (for [block (@page :blocks)]
       [:div
        (case (block :template)
          "nav" [nav-template (block :data)]
          "hero" [hero-template (block :data)])])]))

(defn ^:export run
  []
  (dispatch-sync [:initialize])
  (reagent/render [app-view]
                  (js/document.getElementById "app")))

(defn ^:export reload
  []
  (run))
