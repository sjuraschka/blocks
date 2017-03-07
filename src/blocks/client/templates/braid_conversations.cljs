(ns blocks.client.templates.braid-conversations
  (:require [blocks.client.template :refer [template]]
            [blocks.client.templates.partials.email-field :as email-field]
            [clojure.string :refer [split]]))

(def pad "3em")

(defn blokk-text-mixin [color]
  {:background color
   :color color
   :font-size "0.75em"
   :line-height "1.25em"
   :display "inline"
   :vertical-align "bottom"
   :overflow "hidden"})

(defn styles [data]
  [:&
   {:margin-top "-10em"}

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
       :padding "1.5em"
       :box-shadow [[0 "1px" "2px" 0 "rgba(0,0,0,0.1)"]]
       :box-sizing "border-box"}

      [:.header
       {:opacity 0.25}

       [:.tags
        {:margin-bottom "1.5em"}

        [:.tag
         (blokk-text-mixin "#FD6275")
         {:border-radius "0.15em"
          :margin-right "1em"}]]]

      [:.messages
       {:opacity 0.25}

       [:.message
        {:margin-bottom "1em"}

        [:&:last-child
         {:margin-bottom 0}]

        [:.main
         {:display "flex"}

         [:.avatar
          {:width "2em"
           :height "2em"
           :flex-shrink 0
           :margin-right "1em"
           :border-radius "50%"
           :background "rgba(90, 97, 107, 0.8)"}]

         [:.name
          [:span
           (blokk-text-mixin "#FD6275")]]

         [:.text
          {:vertical-align "top"}

          [:span.word
           (blokk-text-mixin "#ccc")]]]

        [:.embed
         {:margin-top "1em"
          :margin-left "-1.5em"
          :margin-right "-1.5em"
          :padding "0 1.5em"
          :width "100%"
          :height "7em"
          :background "#384d51"}]]]]]]])

(def tag->color
  {"#watercooler" "#6a8a90"
   "#standup" "#3c7784"
   "#braid" "#384251"
   "@rafd" "#999"})

(def conversations
  [{:tags ["#watercooler"]
    :messages [{:user-name "samj"
                :text "#watercooler I'm going to watch the Jays game this Sunday @ 5pm. Anyone want to join?"}
               {:user-name "rafd"
                :text "Count me in!"}
               {:user-name "jamesnvc"
                :text "Can't. Family thing."}]}
   {:tags ["#standup" "#braid" "@rafd"]
    :messages [{:user-name "samj"
                :text "#standup Finished the intro video script. Will be recording today. Blockers: @rafd can you get me the latest logo?"}
               {:user-name "rd"
                :text "Sure, I've added it to the repo."}]}
   {:tags ["#watercooler"]
    :messages [{:user-name "samj"
                :text "#watercooler I'm going to watch the Jays game this Sunday @ 5pm. Anyone want to join?"}
               {:user-name "rafd"
                :text "Count me in!"}
               {:user-name "jamesnvc"
                :text "Can't. Family thing."}]}
   {:tags ["#standup" "#braid" "@rafd"]
    :messages [{:user-name "samj"
                :text "#standup Finished the intro video script. Will be recording today. Blockers: @rafd can you get me the latest logo?"}
               {:user-name "rd"
                :text "Sure, I've added it to the repo."}]}
   {:tags ["#standup" "#braid"]
    :messages [{:user-name "rafd"
                :text "#standup Made good progress on the #braid landing page. Will continue on it today. Should be up for tomorrow. Blockers: none."
                :embed true}
               {:user-name "jamesnvc"
                :text "Sweet. Looking good."}]}])

(defn component [data]
  [:section.braid-conversations
   (into
     [:div.content]
     (for [conversation conversations]
       [:div.conversation
        [:div.header
         (into
           [:div.tags]
           (for [tag (conversation :tags)]
             [:div.tag {:style {:color (tag->color tag)
                                :background-color (tag->color tag)}}
              tag]))]
        (into
          [:div.messages]
          (for [message (conversation :messages)]
            [:div.message
             [:div.main
              [:div.avatar]
              [:div
               [:div.name [:span (message :user-name)]]
               (into
                 [:div.text]
                 (for [word (split (message :text) " ")]
                   [:span
                    [:span.word
                     {:style (when (re-matches #"^(@|#).+" word)
                               {:color (tag->color word)
                                :background-color (tag->color word)})}
                     word]
                    [:span.space " "]]))]]
             (when (message :embed)
               [:div.embed])]))]))])

(defmethod template "braid-conversations" [_]
  {:css styles
   :component component})
