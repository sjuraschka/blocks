(ns blocks.client.templates.roadmap
  (:require [blocks.client.template :refer [template]]))

(def pad "3em")

(defn styles [data]
  [:.roadmap
   {:background (data :background)}

   [:.content
    {:padding "3em"
     :display "flex"
     :flex-direction "column"
     :justify-content "center"
     :align-items "center"}

    [:h1
     {:color (get-in data [:heading :title :color])
      :font-size "2.2em"
      :font-weight "bolder"
      :-webkit-font-smoothing "antialiased"}]

    [:h2
     {:font-size "1.25em"
      :color (get-in data [:heading :subtitle :color])
      :padding-bottom "2rem"
      :text-align "center"
      :-webkit-font-smoothing "antialiased"}]


    [:.kanban
     {:display "flex"
      :justify-content "space-between"}

     [:.phase
      {:background "#fff"
       :flex-basis "22%"
       :padding "0.5em"
       :border-radius "0.25em"
       :box-shadow     [[0 "1px" "2px" 0 "#384d51"]]}

      [:h3
       {:color "#5f6c73"
        :font-size "0.8em"}]

      [:.task
       {:margin "0.5em 0"
        :padding "0.5em"
        :border-radius "0.25em"
        :position "relative"
        :background "#f4f8fb"
        :border "1px solid #ccc"
        :color "#384d51"}

       [:&.complete
        {:padding-right "1em"}

        [:&:after
         {:content "\"\uf058\""
          :font-family "FontAwesome"
          :position "absolute"
          :right "0.5em"
          :top "0.5em"
          :color "#f17130"
          :font-size "1.1em"}]]]]]]])

(defn component [data]
  [:section.roadmap
   [:div.content
    [:h1 (get-in data [:heading :title :text])]
    [:h2 (get-in data [:heading :subtitle :text])]
    [:div.kanban
     (for [[phase tasks] (data :tasks)]
       [:div.phase
        [:h3 phase]
        [:div.tasks
         (for [task tasks]
           [:div.task {:class (when (task :complete?) "complete")} (task :name)])]])]]])

(defmethod template "roadmap" [_]
  {:css styles
   :component component})