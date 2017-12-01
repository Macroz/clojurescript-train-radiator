(ns clojurescript-train-radiator.events
  (:require [re-frame.core :as re-frame]
            [ajax.core :refer [GET]]))

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

(re-frame/reg-event-fx
 :load-trains-response
 (fn [cofx [_ response]]
   {:db (assoc (:db cofx) :trains (js->clj response))}))
