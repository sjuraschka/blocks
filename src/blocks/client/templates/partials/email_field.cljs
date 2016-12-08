(ns blocks.client.templates.partials.email-field
  (:require
    [reagent.core :as r]
    [blocks.client.templates.mixins :refer [button-mixin fontawesome-mixin spin-mixin]]
    [garden.stylesheet :refer [at-media]]
    [ajax.core :refer [ajax-request keyword-request-format keyword-response-format]])
  (:import
    [goog.string]))

(defn styles [data]
  [:form
   [:p.after :p.before
    {:font-size "0.8em"
     :line-height "1.5em"
     :white-space "pre"
     :margin "0.5em"}]

   [:p.before
    {:color (get-in data [:before :color])
     :font-size "1em"}]

   [:p.after
    {:color (get-in data [:after :color])}

    (when (get-in data [:after :icon])
      [:&::after
       (fontawesome-mixin (get-in data [:after :icon]))
       {:display "block"}])]

   [:fieldset
    {:box-shadow [[0 "2px" "5px" 0 "rgba(167,167,167,0.45)"]]
     :display "inline-block"
     :border-radius "5px"
     :overflow "hidden"}

    [:input
     :button
     {:box-sizing "border-box"
      :height "3rem"
      :vertical-align "top"
      :line-height "3rem"
      :outline "none"
      :-webkit-appearance "none"
      :-webkit-border-radius "0px"}]

    [:input
     {:border "none"
      :padding "0 0.75em"
      :background (get-in data [:form :background])}

     ["&[type=email]"
      {:font-size "1.25em"
       :min-width "13em"}

      ["&::-moz-placeholder"
       {:color "#ddd"}]

      ["&::-webkit-input-placeholder"
       {:color "#ddd"}]]]

    [:button
     (button-mixin (get-in data [:button :colors] {}))
     [:&
      {:border-radius 0
       :font-size "1em"
       :-webkit-font-smoothing "antialiased"
       :padding "0 1em"
       :letter-spacing "0.05em"
       :text-transform "uppercase"}]
     [:&::after
      (fontawesome-mixin \uf054) ; chevron-right
      {:margin-left "0.5em"}]]]

   [:&.loading
    [:button::after
     (fontawesome-mixin \uf110) ; spinner
     (spin-mixin)]]
   (at-media {:max-width "450px"}
     [:fieldset
      {:display "flex"
       :flex-direction "column"}

      [:input
       {:width "100%"}]

      ["&[type=email]"
       {:font-size "1em"
        :min-width "9em"}]])])

(defn component [data]
  (let [email (r/atom "")
        loading? (r/atom false)
        submitted? (r/atom false)
        error-message (r/atom nil)]
    (fn [data]
      (let [ajax-request! (fn []
                           (ajax-request {:method (get-in data [:ajax :method])
                                          :uri (get-in data [:ajax :action])
                                          :params (merge
                                                    (get-in data [:ajax :params])
                                                    {:email (goog.string.urlEncode @email)})
                                          ; allowed formats: transit, json, text, raw, url
                                          :format (keyword-request-format (get-in data [:ajax :request-format] :raw) {})
                                          ; allowed formats: transit, json, text, raw, detect
                                          :response-format (keyword-response-format (get-in data [:ajax :response-format] :detect) {})
                                          :handler (fn [[ok response]]
                                                     (reset! loading? false)
                                                     (if ok
                                                       (reset! submitted? true)
                                                       (reset! error-message "Something went wrong. Please try again.")))}))
            submit-form! (fn []
                           (reset! loading? true)
                           (ajax-request!))]

        (if @submitted?
          [:div (data :post-submit-message)]
          [:form {:class (str (when @loading? "loading") " "
                              (when @error-message "error") " ")
                  :on-submit (fn [e]
                               (.preventDefault e)
                               (if (not (re-matches #".+@.+\..+" @email))
                                 (reset! error-message "That doesn't look like an email. Please try again.")
                                 (submit-form!)))}
           (when (get-in data [:before :text])
             [:p.before (get-in data [:before :text])])
           [:fieldset
            [:input {:type "email"
                     :name "email"
                     :value @email
                     :placeholder (get-in data [:placeholder])
                     :auto-focus (when (get-in data [:autofocus]) "autofocus")
                     :on-change (fn [e]
                                  (reset! email (.. e -target -value)))}]
            [:button {} (get-in data [:button :text])]]
           (when (get-in data [:after :text])
             [:p.after (get-in data [:after :text])])
           [:p.error (or @error-message " ")]])))))
