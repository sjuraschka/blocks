(ns blocks.client.templates.secondary-nav
  (:require [blocks.client.template :refer [template]]
            [garden.stylesheet :refer [at-media]]))

(defn styles [data]
  [:.secondary-nav
   {:background (get-in data [:styles :background :color])
    :text-align "center"
    :box-sizing "border-box"
    :border-bottom (get-in data [:styles :border])
    :height (get-in data [:container :height])}

   [:div.nav-content
    {:padding "0 4em"
     :display "flex"
     :height "100%"
     :font-weight (get-in data [:styles :font-weight])
     :justify-content "center"
     :align-items "center"
     :color (get-in data [:styles :color])}
    [:.menu-items
     {:display "flex"
      :justify-content "center"
      :align-items "Center"
      :font-size (get-in data [:styles :font-size])}

     [:.menu-item
      {:margin "0 2em"
       :padding "0.5em 1em"
       :border (get-in data [:styles :border])
       :border-radius "30px"
       :cursor "pointer"
       :color (get-in data [:styles :color])
       :text-decoration "none"}
      [:&:hover
       {:background "#eee"}]
        ;:border (get-in data [:styles :bold-border])}]
      [:&.final:hover
       {:background "#3D50CF"
        :color "#fff"}]]
     [:hr
      {:width "4em"
       :height "1px"
       :border "none"
       :border-bottom "1px solid #ccc"}]]]
   (at-media {:max-width "800px"}
     [:&
      {:display "none"}])])

(defn component [data]
  [:div.secondary-nav
     [:div.nav-content
      (into
        (for [item (data :menu)]
         [:div.menu-items
          [:a.menu-item.white {:href (item :url)}
           (item :title)]
          [:hr]]))
      [:div.menu-items
       [:a.menu-item.final {:href (get-in data [:title-final :url])}
        (get-in data [:title-final :text])]]]])

(defmethod template "secondary-nav" [_]
  {:css styles
   :component component})
