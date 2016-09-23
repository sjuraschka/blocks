(ns blocks.client.templates.braid-conversations
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.partials.email-field :as email-field]
            [clojure.string :refer [split]]))

(def pad "3em")

(defn blokk-text-mixin []
  {:background "#ccc"
   :color "#ccc"
   :font-size "0.75em"
   :display "inline"
   :vertical-align "bottom"
   :overflow "hidden"})

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
     {:text-align "left"
      :background "#fff"
      :width "18em"
      :border-radius "3px 3px 0 0"
      :box-shadow [[0 "1px" "2px" 0 "#ccc"]]
      :padding "1.5em"
      :box-sizing "border-box"
      }

     [:.tags

      [:.tag
       (blokk-text-mixin)]]


     [:.message
      {:display "flex"
       :margin-bottom "1em"}

     [:.avatar
      {:width "2em"
       :height "2em"
       :flex-shrink 0
       :margin-right "1em"
       :border-radius "50%"
       :background "black"}]

     [:.text
      {:line-height "1.25em"
       :vertical-align "top"}

      [:span.word
       (blokk-text-mixin)]]]]]]])

(def conversations
  [{:tags [{:name "standup"
            :color "blue"}]
    :messages [{:user-name "Raf"
               :text "how are people doing today how are people doing today?"}
               {:user-name "James"
                :text "how are people doing today?"}
               {:user-name "Bob"
                :text "how are people doing today?"}
               {:user-name "Bob"
                :text "how are people doing today?"}]}
   {:tags [{:name "watercooler"
            :color "blue"}]
    :messages [{:user-name "Samantha"
                :text "how are people doing today?"}
               {:user-name "Bob"
                :text "how are people doing today?"}
               {:user-name "Bob"
                :text "how are people doing today?"}
               {:user-name "James"
                :text "how are people doing today?"}]}
   {:tags [{:name "watercooler"
            :color "red"}]
    :messages [{:user-name "Bob"
                :text "how are people doing today?"}
               {:user-name "Bob"
                :text "how are people doing today?"}]}])


(defn component [data]
  [:section.braid-conversations
   [:div.content
    (for [conversation conversations]
      [:div.conversation {:style {:height "15em"}}
       [:div.header
        [:div.tags
         (for [tag (conversation :tags)]
           [:div.tag {:style {:color (tag :color)
                              :background-color (tag :color)}}
            (tag :name)])]]
       [:div.messages
        (for [message (conversation :messages)]
          [:div.message
           [:div.avatar ]
           [:div.text
            (for [word (split (message :text) " ")]
              [:span
               [:span.word word]
               [:span.space " "]])]])]])]])

(defmethod template "braid-conversations" [_]
  {:css styles
   :component component})
