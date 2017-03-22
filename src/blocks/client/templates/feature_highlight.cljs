(ns blocks.client.templates.feature-highlight
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(def pad "4em")

(defn styles [data]
  [:.feature-highlight
   {:padding pad
    :background (get-in data [:background :color])}

   [:.content
    {:display "flex"
     :justify-content "center"
     :flex-wrap "wrap"
     :align-items "center"}

    [:&.right
     [:.text
      {:margin-right (get-in data [:spacing :right])}]]

    [:&.left
     {:flex-direction "row-reverse"}
     [:.text
      {:margin-left (get-in data [:spacing :left])}]]]

   [:.text
    {:min-width "15em"
     :display "flex"
     :flex-direction "column"
     :align-items "center"
     :max-width "30em"
     :text-align (data :alignment)
     :flex-basis "30%"
     :margin "3em 0"}

    [:div.circle
     {:font-family "apple-system, BlinkMacSystemFont, Roboto, \"Droid Sans\", \"Helvetica Neue\", Helvetica,Arial,sans-serif;"
      :display "inline-block"
      :margin-bottom "0.75rem"
      :width "1.75em"
      :height "1.75em"
      :line-height "1.75em"
      :text-align "center"
      :background "#fff"
      :border "1px solid #eee"
      :box-shadow "0 1px 3px 0 rgba(148,145,145,0.30)"
      :border-radius "50%"
      :color "#232B69"}]]

   [:h1
    {:color (get-in data [:heading :color])
     :font-size (get-in data [:heading :font-size])
     :letter-spacing (get-in data [:heading :spacer])
     :text-transform (get-in data [:heading :text-transform])
     :font-weight (get-in data [:heading :font-weight])
     :margin-bottom "1.5rem"
     :position "relative"
     :z-index 0}]

   [:.underline
    {:margin-top "-1em"}]

   [:p
    {:font-size "0.85em"
     :color (get-in data [:description :color])
     :font-weight (get-in data [:description :weight])
     :white-space (get-in data [:description :white-space])
     :line-height (get-in data [:description :line-height])}]

   [:a
    {:text-decoration "none"
     :color (get-in data [:circle :color])}]

   [:img.feature
    {:max-width "32em"
     :margin-top "2em"}]

   (at-media {:max-width "575px"}

     [:img
      {:max-width "25em"}]
     [:.content.left :.content.right
      [:.text
       {:margin-left 0
        :margin-right 0}
       [:h1
        {:font-size "1.5em"}]
       [:p
        {:white-space "normal"}]]])])

(defn component [data]
  [:section.feature-highlight
   [:a {:name (data :anchor)}]
   [:div.content {:class (get-in data [:image :position])}
    [:div.text
     (when (get-in data [:heading :step])
       [:div.circle
        [:div (get-in data [:heading :step])]])
     [:h1 {:data-step (get-in data [:heading :step])}
      (get-in data [:heading :text])]
     [:img.underline {:src (get-in data [:heading :underline])}]

     [:p {:dangerouslySetInnerHTML
          {:__html (get-in data [:description :text])}}]]

    [:div
     [:img.feature {:src (get-in data [:image :image-url])}]]]])

(defmethod template "feature-highlight" [_]
  {:css styles
   :component component})
