(ns utils
  (:require [re-frame.core :as rf]))

(defn <s [v] @(rf/subscribe v))
(defn >e [v] (rf/dispatch v))
