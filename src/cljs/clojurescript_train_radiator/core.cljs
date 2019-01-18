(ns clojurescript-train-radiator.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [ajax.core :refer [GET]]
            [cljs-time.core :as time]
            [cljs-time.local :as localtime]))

(def debug? ^boolean goog.DEBUG)

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (println "dev mode")))

;;
;; Accessing the API
;;

(def TRAIN_API "https://rata.digitraffic.fi/api/v1/live-trains")

(defn get-trains [station]
  (GET TRAIN_API
       {:params {:station station}
        :response-format :json
        :keywords? true
        :handler #(re-frame/dispatch [:load-trains-response %])}))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   {}))

(re-frame/reg-event-fx
 :load-trains
 (fn [cofx _]
   (get-trains "HKI")
   {}))

(re-frame/reg-event-db
 :load-trains-response
 (fn [db [_ response]]
   (assoc db :trains (js->clj response))))


;;
;; Subscriptions
;;


(re-frame/reg-sub
 :trains
 (fn [db]
   (:trains db)))

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
     [:h1 "Trains trains trains"]
     [render @trains]]))

(defn main-panel []
  [page])

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (re-frame/dispatch [:load-trains])
  (dev-setup)
  (mount-root))
