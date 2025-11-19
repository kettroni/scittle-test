(ns canvas
  (:require [re-frame.core :as rf]
            [utils :refer [>e]]))

(defn canvas-ctx! []
  (let [canvas (-> js/document
                   (.getElementById "canvas"))
        ctx    (-> canvas
                   (.getContext "2d"))]
    [canvas ctx]))

(defn clear-bg! [ctx canvas]
  (doto ctx
    (.clearRect 0, 0, canvas.width, canvas.height)))

(defn set-fill-style! [ctx c]
  (set! (.-fillStyle ctx) c))

(defn fill-rect! [ctx x y w h c]
  (doto ctx
    (set-fill-style! (or c "black"))
    (.fillRect x y w h)))

(rf/reg-fx ::test
           (fn [{:keys [x y w h c]}]
             (let [[_ ctx] (canvas-ctx!)]
               (doto ctx
                 (set-fill-style! c)
                 (.fillRect x y w h)))))

(rf/reg-fx ::clear-background
           (fn [c]
             (let [[canvas ctx] (canvas-ctx!)]
               (clear-bg! ctx canvas)
               (when c
                 (fill-rect! ctx
                             0
                             0
                             canvas.width
                             canvas.height
                             c)))))

(rf/reg-event-fx ::clear
                 (fn [_ [_ c]]
                   {::clear-background c}))

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

(comment

  (for [x (range 0 100 10)]
    (>e [::fill-rect {:x x
                      :y x
                      :w (- 100 x)
                      :h (- 100 x)
                      :c (-> ["red" "white" "black"]
                             (nth (mod x 3)))
                      }]))

  (>e [::clear "red"])

  )
