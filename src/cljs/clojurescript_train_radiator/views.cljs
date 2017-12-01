(ns clojurescript-train-radiator.views
  (:require [re-frame.core :as re-frame]
            [clojurescript-train-radiator.subs :as subs]
            [cljs-time.core :as time]
            [cljs-time.local :as localtime]
            ))

;;
;; Time manipulation
;;

(defn show-time [t]
  (localtime/format-local-time t :hour-minute))

(defn in-the-future? [t]
  (time/before? (localtime/local-now) (localtime/to-local-date-time t)))

;;
;; Generating HTML
;;

(defn timetable [row]
  (let [scheduled (:scheduledTime row)
        actual (:actualTime row)]
    [:li
     (show-time scheduled)
     (when actual
       (list
        "->"
        (show-time actual)))
     (when (in-the-future? scheduled)
       "***")
     " "
     (:type row)
     " "
     (:stationShortCode row)]))

(defn train [t]
  [:li
   (:trainNumber t)
   " "
   (:trainType t)
   " "
   (:trainCategory t)
   " "
   (:commuterLineID t)
   [:ul
    (map-indexed (fn [i row] ^{:key i} [timetable row])
                 (take 5 (:timeTableRows t)))]])

(defn render [trains]
  [:ul
   (for [t trains]
     ^{:key (:trainNumber t)} [train t])])

(defn page []
  (let [trains (re-frame/subscribe [:trains])]
    [:div
     [:h1 "Trains"]
     [render @trains]]))

(defn main-panel []
  [page])
