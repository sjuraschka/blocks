(ns blocks.client.templates.secondary-nav
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.secondary-nav
   {:background (get-in data [:styles :background :color])
    :border-bottom (get-in data [:styles :border])
    :height (get-in data [:container :height])
    :display "flex"
    :align-items "center"
    :justify-content "center"
    :padding "0 8em" 
    :box-sizing "border-box"}

   [:>a
    {:display "block"
     :margin "0 2em"
     :padding "0.5em 1em"
     :white-space "nowrap"
     :border (get-in data [:styles :border])
     :border-radius "30px"
     :cursor "pointer"
     :color (get-in data [:styles :color])
     :text-decoration "none"
     :font-weight (get-in data [:styles :font-weight])
     :font-size (get-in data [:styles :font-size])}
    
    [:&:hover
     {:background "#eee"}]

    [:&:last-child:hover
     {:background "#3D50CF"
      :color "#fff"}]]

   [:>hr
    {:width "4em"
     :height "1px"
     :border "none"
     :border-bottom "1px solid #ccc"}]
   
   (at-media {:max-width "800px"}
     [:&
      {:display "none"}])])

(defn component [data]
  (into 
    [:div.secondary-nav]
    (interpose 
      [:hr]
      (for [item (data :menu)]
        [:a {:href (item :url)}
         (item :title)]))))

(defmethod template "secondary-nav" [_]
  {:css styles
   :component component})
