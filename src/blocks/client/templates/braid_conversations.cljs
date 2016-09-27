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
     ;:align-items "flex-end"
     }

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
       (blokk-text-mixin)
       {:border-radius "0.15em"}]]

     [:.message
      {:display "flex"
       :margin-bottom "1em"
       :margin-top "1em"}

     [:.avatar
      {:width "2em"
       :height "2em"
       :flex-shrink 0
       :margin-right "1em"
       :border-radius "50%"
       :background "rgba(90, 97, 107, 0.8)"}]

     [:.text
      {:line-height "1.25em"
       :vertical-align "top"}

      [:span.word
       (blokk-text-mixin)]]]]]]])

(def conversations
  [{:tags [{:name "standup"
            :color "#3c7784"}]
    :messages [{:user-name "Raf"
               :text "how are people doing today how are people doing today,
                      I'm launching new landing page this week!"}
               {:user-name "James"
                :text "Yes! What logo did we decide on?
                       I'm working on Rookie this week."}
               {:user-name "Sammyjwalk"
                :text "That's geat @Raf - I'm working on the video for the landing page!"}
               {:user-name "Bob"
                :text "how are people doing today?"}]}
   {:tags [{:name "watercooler"
            :color "#6a8a90"}]
    :messages [{:user-name "Samantha"
                :text "What are you up to this weekend?"}
               {:user-name "Heather"
                :text "I've never gone to the CN tower! I'm going to check it out and watch the jays game."}
               {:user-name "Jamie"
                :text "I'm super boring, I'm working! Really want to learn php and work on Braid."}
               {:user-name "James"
                :text "Good for you, message me if you need any help."}]}
   {:tags [{:name "clojure nation"
            :color "#384251"}]
    :messages [{:user-name "lindsay"
                :text "Have you seen James giphybot he built?"}
               {:user-name "brittany"
                :text "Yes!!! It is slightly addicting."}
               {:user-name "Mike"
                :text "I've been wanting to build an integration for Braid, something to help me read my gmail messages in Braid"}]}])


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
