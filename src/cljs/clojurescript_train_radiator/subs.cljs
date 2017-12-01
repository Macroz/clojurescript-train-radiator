(ns clojurescript-train-radiator.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :trains
 (fn [db]
   (:trains db)))
