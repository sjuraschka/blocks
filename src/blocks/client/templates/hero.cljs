(ns braid.client.templates.hero)

(defn hero-template [data]
  [:div.hero
   [:div.heading
    (data :heading)]])
