(ns blocks.client.templates.roadmap
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(def pad "2em")

(defn styles [data]
  [:.roadmap
   {:background (data :background)}

   [:.content
    {:padding pad
     :display "flex"
     :flex-direction "column"
     :justify-content "center"
     :align-items "center"}

    [:h1
     {:color (get-in data [:heading :title :color])
      :font-size "1.75rem"}]

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
      {:margin-right "1em"
       :background (get-in data [:phase :background])
       :box-shadow "0 8px 15px 0px rgba(0,0,0,0.05)"
       :border "1px solid #eee"
       :padding "0.5em"
       :border-radius "0.25em"}

      [:&:last-child
       {:margin-right 0}]

      [:h3
       {:color "#5f6c73"
        :margin-left "0.5em"
        :line-height "1.5em"}]

      [:.task
       {:margin "0.5em 0"
        :padding "0.5em"
        :border-radius "0.25em"
        :border "1px solid #90b0fd"
        :background "#f4f7ff"
        :position "relative"
        :color "#384d51"}

       [:&:last-child
        {:margin-bottom 0}]

       [:&.complete
        {:padding-right "2em"
         :background "#ecf8f9"
         :border "1px solid #55c1c4"}

        [:&:after
         {:content "\"\uf00c\""
          :font-family "FontAwesome"
          :position "absolute"
          :right "0.5em"
          :top "0.5em"
          :color "#55c1c4"
          :font-size "0.85em"}]]]]]]
   (at-media {:max-width "600px"}
     [:&
      [:.content
       [:.kanban
        {:flex-direction "column"}
        [:.phase
         {:margin-right 0
          :margin-bottom "1em"
          :width "20rem"}]]]])])

(defn component [data]
  [:section.roadmap
   [:div.content
    [:h1 (get-in data [:heading :title :text])]
    [:h2 (get-in data [:heading :subtitle :text])]
    (into
      [:div.kanban]
      (for [[phase tasks] (data :tasks)]
        [:div.phase
         [:h3 phase]
         (into
           [:div.tasks]
           (for [task tasks]
             [:div.task {:class (when (task :complete?) "complete")} (task :name)]))]))]])

(defmethod template "roadmap" [_]
  {:css styles
   :component component})
