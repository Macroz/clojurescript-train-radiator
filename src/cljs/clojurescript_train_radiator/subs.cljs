(ns clojurescript-train-radiator.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :loading?
 (fn [db]
   (:loading? db)))

(re-frame/reg-sub
 :trains
 (fn [db]
   (:trains db)))
