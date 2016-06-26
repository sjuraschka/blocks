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
                                   subscribe]]
            [blocks.client.template :refer [template]]
            blocks.client.templates.nav
            blocks.client.templates.hero
            [garden.core :refer [css]]
            [garden.stylesheet :refer [at-import]]))

(def styles
  [(at-import "/css/reset.css")
   (at-import "//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css")
   (at-import "//fonts.googleapis.com/css?family=Montserrat:700|Lato")

  [:body
   {:font-family "Lato"
    :line-height "1.25"
    :overflow "hidden"}]

  [:section
   {:overflow "hidden"}

   [:.content
    {:max-width "1000px"
     :position "relative"
     :margin "0 auto"}]]])

(register-handler
  :initialize
  (fn [state _]
    (cljs.reader/read-string (.-innerHTML (js/document.getElementById "data")))))

(register-sub
  :page
  (fn [state _]
    (reaction (@state :page))))

(enable-console-print!)

(defn app-view []
  (let [page (subscribe [:page])]
    [:div.app
     [:style {:type "text/css"
              :dangerouslySetInnerHTML
              {:__html (css
                         styles
                         (for [block (@page :blocks)]
                           (:css (template (block :template)))))}}]
     (for [block (@page :blocks)]
       [:div
        [(:component (template (block :template))) (block :data)]])]))

(defn ^:export run
  []
  (dispatch-sync [:initialize])
  (reagent/render [app-view]
                  (js/document.getElementById "app")))

(defn ^:export reload
  []
  (run))
