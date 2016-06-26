(ns blocks.client.templates.mixins)

(defn fontawesome-mixin [unicode]
  {:content (str "\"" unicode "\"")
   :font-family "FontAwesome"})

(def button-mixin
  [:&
   {:border "none"
    :border-radius "0.5rem"
    :padding "1.5rem 1.75rem"
    :font-size "1.5rem"
    :font-weight "bold"
    :cursor "pointer"
    :outline "none"
    :font-family "Lato"
    :text-decoration "none"
    :display "inline-block"
    :color "white"
    :background "#0D6C7F"
    :transition "background 0.1s ease-in-out"}

   [:&:hover
    {:background "#014F75"}]

   [:&:active
    {:background "#012156"}]])
