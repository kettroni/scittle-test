(ns script
  (:require [canvas :as canvas]
            [counter :as counter]
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
       [counter/app]

       ::canvas
       [canvas/app]

       [:div
        [:button
         {:on-click #(>e [::change-content ::counter])}
         "Counter"]
        [:button
         {:on-click #(>e [::change-content ::canvas])}
         "Canvas"]
        ])]))

;; render
(defn render []
  (rdom/render
   [app]
   (-> js/document
       (.getElementById "app"))))

(render)

(comment

  (>e [::change-content nil])

  ;; canvas
  (>e [::change-content ::canvas])

  (>e [::canvas/fill-rect {:x 10
                           :y 10
                           :w 20
                           :h 20
                           :c "blue"}])

  (let [[canvas _] (canvas/canvas-ctx!)]
    (for [x (range 0 canvas.width 10)]
      (>e [::canvas/fill-rect {:x x
                               :y x
                               :w (- canvas.width x)
                               :h (- canvas.height x)
                               :c (-> ["red" "white" "black"]
                                      (nth (mod x 3)))}])))

  (>e [::canvas/clear "black"])

  (>e [::canvas/clear])

  ;; counter
  (>e [::change-content ::counter])

  (>e [::counter/click])

  (<s [::counter/clicks]))
