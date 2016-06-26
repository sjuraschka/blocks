(ns blocks.client.templates.mixins)

(defn fontawesome-mixin [unicode]
  {:content (str "\"" unicode "\"")
   :font-family "FontAwesome"})

(defn button-mixin [colors]
  [:&
   {:border-radius "0.5rem"
    :padding "1.5rem 1.75rem"
    :font-size "1.5rem"
    :font-weight "bold"
    :cursor "pointer"
    :outline "none"
    :font-family "Lato"
    :text-decoration "none"
    :display "inline-block"
    :color (colors :text)
    :background-color (colors :bg)
    :transition "background 0.1s ease-in-out"}
   (if (colors :border)
     {:border (str "2px solid " (colors :border))}
     {:border "none"})

   [:&:hover
    {:background (colors :bg-hover)}]

   [:&:active
    {:background (colors :bg-active)}]])
