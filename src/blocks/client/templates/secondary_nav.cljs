(ns blocks.client.templates.secondary-nav
  (:require [blocks.client.template :refer [template]]))

(defn styles [data]
  [:.secondary-nav
   {:background (get-in data [:background :color])
    :text-align "center"
    :box-sizing "border-box"
    :border-bottom "1px solid #eee"
    :height "4em"}

   [:div.nav-content
    {:padding "0 5em"
     :display "flex"
     :height "100%"
     :font-weight 900
     :justify-content "center"
     :align-items "center"
     :color (data :color)}
    [:.menu-items
     {:display "flex"
      :justify-content "center"
      :align-items "Center"
      :font-size "0.85em"}

     [:.menu-item
      {:margin "0 2em"
       :padding "0.5em 1em"}
      [:&:hover
       {:border "1px solid #ccc"
        :border-radius "30px"}]
      [:&.final:hover
       {:background "#3D50CF"
        :color "#fff"}]]
     [:hr
      {:width "4em"
       :height "1px"
       :border "none"
       :border-bottom "1px solid #ccc"}]]]])

(defn component [data]
  [:div.secondary-nav
     [:div.nav-content
      (into
        (for [item (data :menu)]
         [:div.menu-items
          [:div.menu-item.white (item :title)]
          [:hr]]))
      [:div.menu-items
       [:div.menu-item.final "Get Paid"]]]])

(defmethod template "secondary-nav" [_]
  {:css styles
   :component component})
