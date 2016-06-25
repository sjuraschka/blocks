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

(defn app-view []
  [:div "Hello World!"])

(defn ^:export run
  []
  ;(dispatch-sync [:initialize])
  (reagent/render [app-view]
                  (js/document.getElementById "app")))

