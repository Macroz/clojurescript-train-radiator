(ns clojurescript-train-radiator.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :loading-trains?
 (fn [db]
   (:loading-trains? db)))

(re-frame/reg-sub
 :loading-stations?
 (fn [db]
   (:loading-stations? db)))

(re-frame/reg-sub
 :station
 (fn [db]
   (:station db)))

(re-frame/reg-sub
 :stations
 (fn [db]
   (:stations db)))

(re-frame/reg-sub
 :trains
 (fn [db]
   (:trains db)))
