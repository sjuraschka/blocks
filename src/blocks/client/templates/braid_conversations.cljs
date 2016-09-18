(ns blocks.client.templates.braid-conversations
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.partials.email-field :as email-field]))

(def pad "3em")

(defn styles [data]
  [:&
   {
    :margin-top "-5em"
    }
  [:.braid-conversations
   {:padding [[0 pad]]
    :background (get-in data [:background :color])
    :text-align "center"
    :min-width "60em"}

   [:.content
    {:display "flex"
     :justify-content "space-between"
     :align-items "flex-end"}

    [:.conversation
     {:background "#fff"
      :width "18em"
      :height "10em"
      :border-radius "3px 3px 0 0"
      :box-shadow [[0 "1px" "2px" 0 "#ccc"]]
      }]


    ]]])


(defn component [data]
  [:section.braid-conversations
   [:div.content
    [:div.conversation {:style {:height "15em"}}]
    [:div.conversation {:style {:height "10em"}}]
    [:div.conversation {:style {:height "20em"}}]


    ]])

(defmethod template "braid-conversations" [_]
  {:css styles
   :component component})
