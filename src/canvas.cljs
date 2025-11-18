(ns canvas
  (:require [re-frame.core :as rf]
            [utils :refer [>e]]))

(defn ctx! []
  (let [canvas (-> js/document
                   (.getElementById "canvas"))
        ctx    (-> canvas
                   (.getContext "2d"))]
    ctx))

(defn set-fill-style! [ctx c]
  (set! (.-fillStyle ctx) c))

(rf/reg-fx ::test
           (fn [{:keys [x y w h c]}]
             (let [canvas (-> js/document
                              (.getElementById "canvas"))
                   ctx    (ctx!)]
               (doto ctx
                 (set-fill-style! c)
                 (.clearRect 0, 0, canvas.width, canvas.height)
                 (.fillRect x y 100 100)
                 ))
               ))

(rf/reg-event-fx ::fill-rect
                 (fn [_ [_ ks]]
                   {::test ks}))

(defn app []
  [:div
   [:button
    {:on-click #(>e [::fill-rect {:x 0
                                  :y 0
                                  :w 100
                                  :h 100
                                  :c "red"}])}
    "Red box"]
   [:canvas {:id "canvas"}]])
