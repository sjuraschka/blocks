(ns blocks.client.templates.mixins)

(defn fontawesome-mixin [unicode]
  {:content (str "\"" unicode "\"")
   :font-family "FontAwesome"})

(defn button-mixin [colors]
  [:&
   {:-webkit-font-smoothing "antialiased"
    :border-radius "3px"
    :padding "1rem 1.75rem"
    :font-size "1.1rem"
    :font-weight "bold"
    :cursor "pointer"
    :outline "none"
    :text-align "center"
    :box-sizing "border-box"
    :text-decoration "none"
    :display "inline-block"
    :color (colors :text)
    :background-color (colors :bg)
    :transition "background 0.1s ease-in-out"
    :box-shadow (colors :shadow)}
   (if (colors :border)
     {:border (str "2px solid " (colors :border))}
     {:border "none"})

   [:&:hover
    {:background (colors :bg-hover)}]

   [:&:active
    {:background (colors :bg-active)}]])

(defn spin-mixin []
  {:animation [["anim-spin" "1s" "infinite" "steps(8)"]]
   :display "inline-block"})
