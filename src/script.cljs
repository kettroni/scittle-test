(ns script
  (:require [counter :as c]
            [re-frame.core :as rf]
            [reagent.dom :as rdom]
            [utils :refer [<s >e]]))

;; events
(rf/reg-event-db ::change-content
                 (rf/path [:content])
                 (fn [_ [_ content-name]]
                   content-name))

(rf/reg-sub ::content :-> :content)

;; components
(defn app []
  (let [content (<s [::content])]
    [:div
     (when content
       [:button
        {:on-click #(>e [::change-content nil])}
        "<-"])
     (case content
       ::counter
       [c/app]

       [:table
        [:tr [:td [:button
                   {:on-click #(>e [::change-content ::counter])}
                   "Counter"]]]])]))

;; render
(defn render []
  (rdom/render
   [app]
   (-> js/document
       (.getElementById "app"))))

(render)

(comment

  (>e [::change-content ::counter])

  (>e [::change-content nil])

  (>e [::c/click])

  (<s [::c/clicks])

  )
