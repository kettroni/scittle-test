(ns counter
  (:require [re-frame.core :as rf]
            [utils :refer [<s >e]]))

;; events
(rf/reg-event-db ::click
 (rf/path [:clicks])
 inc)

;; subs
(rf/reg-sub ::clicks :-> :clicks)

;; components
(defn app []
  [:div
   "Clicks: " (<s [::clicks])
   [:p [:button
        {:on-click #(>e [::click])}
        "Click me!"]]])

(comment
  (<s [::clicks])
  
  (>e [::click])

  )
