(ns blocks.client.templates.downloads
  (:require
    [clojure.string :as string]
    [blocks.client.template :refer [template]]
    [blocks.client.templates.mixins :refer [fontawesome-mixin button-mixin]]
    [garden.stylesheet :refer [at-media]]))


(defn get-platform []
  (let [ua js/window.navigator.userAgent
        platform js/window.navigator.platform]
    (cond
      (or (= platform "MacIntel") (= platform "MacPPC"))
      :mac-os-x
      (= platform "CrOS")
      :chrome-os
      (or (= platform "Win32") (= platform "Win64"))
      :windows
      (string/includes? ua "Android")
      :android
      (string/includes? platform "Linux")
      :linux
      (string/includes? ua "Windows")
      :windows
      (string/includes? ua "iP")
      :ios)))


(defn styles [data]
  [:>.downloads
   {:background "#f7f8f9"
    :padding "3em"}

   [:>.recommended
    :>.other
    {:display "flex"
     :justify-content "center"}

    [:>.spacer
     {:width "2em"}]]

   [:>.recommended
    {:text-align "center"
     :margin "2em"}

    [:>.download-option-main
     [:>a
      (button-mixin {:text "#fff"
                     :bg "#3D75FB"
                     :shadow "#000000"
                     :bg-hover "#054aef"
                     :bg-active "#0440ce"})
      {:width "14rem"}]

     [:>.platform
      {:margin-top "0.5em"}]]]

   [:>.other
     {:line-height "2em"}
    [:.mobile, :.desktop
     {:width "17rem"
      :padding-left "0.5rem"
      :text-align "left"}]

    [:.download-option
     {:color "#3D75FB"}

     [:a
      {:color "#3D75FB"}]

     [:&::before
      {:display "inline-block"
       :width "1em"
       :text-align "center"
       :margin-right "0.25em"}]

     [:&.linux::before
      (fontawesome-mixin \uf17c)]
     [:&.mac-os-x:before (fontawesome-mixin \uf179)]
     [:&.ios:before (fontawesome-mixin \uf179)]
     [:&.android:before (fontawesome-mixin \uf17b)]
     [:&.windows:before (fontawesome-mixin \uf17a)]
     [:&.web:before (fontawesome-mixin \uf268)]]]
   (at-media {:max-width "750px"}
     [:&
      {:padding "2em"}
      [:>.recommended
       :>.other
        {:flex-direction "column"
         :justify-content "center"
         :align-items "center"}

       [:>.spacer
        {:width 0
         :display "none"}]]
      [:>.recommended
       [:>.download-option-main:first-child
        {:margin-bottom "1em"}]]])])


(def download-options
  [{:name "Android"
    :id :android
    :url "...."
    :coming-soon true
    :mobile? true}
   {:name "Mac OS X"
    :id :mac-os-x
    :url "...."
    :coming-soon true
    :desktop? true}
   {:name "Windows"
    :id :windows
    :url "...."
    :coming-soon true
    :desktop? true}
   {:name "iOS"
    :id :ios
    :url "...."
    :coming-soon true
    :mobile? true}
   {:name "Linux"
    :id :linux
    :url "...."
    :coming-soon true
    :desktop? true}])


(defn download-option-view [option]
  [:div.download-option
   {:class (name (option :id))}
   [:a {:href (option :url)} (:name option)]])

(defn main-download-option-view [option]
  [:div.download-option-main
   {:class (name (option :id))}
   [:a {:href (option :url)} (or (option :text) "Download Braid")]
   [:div.platform "For " (:name option)]])


(defn component [data]
  [:section.downloads
   [:div.recommended
    (let [platform (get-platform)
          option (or (->> download-options
                          (filter (fn [o]
                                    (= (o :id) platform)))
                          first)
                     (download-options :web))]

      [main-download-option-view option])
    [:div.spacer]
    [main-download-option-view {:text "Launch in Browser"
                                :name "Chrome, Firefox and Safari"
                                :id :web
                                :url "...."}]]

   [:div.other
    [:div.mobile
     [:h2 "Download for phone or tablet:"]
     (for [option (filter :mobile? download-options)]
       [download-option-view option])]
    [:div.spacer]
    [:div.desktop
     [:h2 "Download for other desktop OS:"]
     (for [option (filter :desktop? download-options)]
       [download-option-view option])]]])


(defmethod template "downloads" [_]
  {:css styles
   :component component})
