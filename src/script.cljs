(require '[reagent.dom :as rdom]
         '[re-frame.core :as rf])

;; utils
(defn <s [v] @(rf/subscribe v))
(defn >e [v] (rf/dispatch v))

;; events
(rf/reg-event-db ::click
 (rf/path [:clicks])
 (fnil inc 0))

;; subs
(rf/reg-sub ::clicks :-> :clicks)

;; components
(defn app []
  [:div
   "Clicks: " (<s [::clicks])
   [:p [:button
        {:on-click #(>e [::click])}
        "Click me!"]]])

;; render
(rdom/render
 [app]
 (-> js/document
     (.getElementById "app")))
