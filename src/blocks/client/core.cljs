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
            blocks.client.templates.header
            blocks.client.templates.hero
            blocks.client.templates.hero-middle
            blocks.client.templates.features-grid
            blocks.client.templates.footer
            blocks.client.templates.cta
            blocks.client.templates.feature-highlight
            [garden.core :refer [css]]
            [garden.stylesheet :refer [at-import]]
            [figwheel.client :as fig]
            [ajax.core :refer [GET]]))

(def styles
  [(at-import "//maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css")
   (at-import "//fonts.googleapis.com/css?family=Montserrat:700|Lato")

  [:body
   {:font-family "Lato"
    :line-height "1.25"}]

  [:.block
   {:overflow "hidden"}

   [:.content
    {:max-width "1000px"
     :position "relative"
     :margin "0 auto"}]]])

(register-handler
  :set-data
  (fn [state [_ data]]
    data))

(register-handler
  :fetch-data
  (fn [state _]
    (GET (str "/api/domains/" (get-in state [:page :domain])
              "/pages" (get-in state [:page :url]))
      {:handler (fn [data]
                  (println "Reloading EDN")
                  (println data)
                  (dispatch [:set-data (cljs.reader/read-string data)]))})
    state))

(register-sub
  :page
  (fn [state _]
    (reaction (@state :page))))

(enable-console-print!)

(defn app-view []
  (let [page (subscribe [:page])]
    (fn []
      (let [blocks (->> (@page :blocks)
                        (map-indexed (fn [index b]
                                       (assoc b :id (str "block-" index)))))]
        [:div.app
         [:div.styles
          [:style {:type "text/css"
                   :dangerouslySetInnerHTML
                   {:__html (css styles)}}]
          (for [block blocks]
            ^{:key (block :id)}
            [:style {:type "text/css"
                     :dangerouslySetInnerHTML
                     {:__html (css
                                [(str "#" (block :id))
                                 ((:css (template (block :template))) (block :data))])}}])]
         (for [block blocks]
           ^{:key (block :id)}
           [:div {:id (block :id) :class "block"}
            [(:component (template (block :template))) (block :data)]])]))))

(defn render []
  (reagent/render [app-view]
                  (js/document.getElementById "app")))

(defn ^:export run
  []
  (dispatch-sync [:set-data (cljs.reader/read-string (.-innerHTML (js/document.getElementById "data")))])
  (render))

(fig/add-message-watch
  :edn-watcher
  (fn [{:keys [msg-name] :as msg}]
    (when (= msg-name :edn-files-changed)
      (dispatch [:fetch-data]))))

(defn ^:export reload
  []
  (render))
