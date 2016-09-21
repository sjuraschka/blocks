(ns blocks.client.templates.roadmap
  (:require [blocks.client.template :refer [template]]))

(def pad "3em")

(defn styles [data]
  [:.roadmap

   [:.content

    [:.kanban
     {:display "flex"
      :justify-content "space-between"}

     [:.phase
      {:background "#DFDFDF"
       :flex-basis "22%"
       :padding "0.5em"
       :border-radius "0.25em"}

      [:.task
       {:background "white"
        :margin "0.5em 0"
        :padding "0.5em"
        :border-radius "0.25em"
        :position "relative"}

       [:&.complete
        {:padding-right "1em"}

        [:&:after
         {:content "\"\uf058\""
          :font-family "FontAwesome"
          :position "absolute"
          :right "0.5em"
          :top "0.5em"
          :color "green"
          :font-size "1.1em"}]]]]]]])

(defn component [data]
  [:section.roadmap
   [:div.content
    [:h1 "Roadmap"]
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
