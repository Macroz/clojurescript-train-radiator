(ns clojurescript-train-radiator.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

;;
;; Accessing the API
;;

(def TRAIN_API "https://rata.digitraffic.fi/api/v1/live-trains")
(def STATIONS_API "https://rata.digitraffic.fi/api/v1/metadata/stations")

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   {:loading-stations? true}))

(re-frame/reg-event-fx
 :set-station
 (fn [{:keys [db]} [_ station]]
   {:db (assoc db :station station)
    :dispatch [:load-trains]}))

(re-frame/reg-event-fx
 :load-stations
 (fn [{:keys [db]} _]
   {:db (assoc db :loading-stations? true)
    :http-xhrio {:method :get
                 :uri STATIONS_API
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:load-stations-response]
                 :on-failure [:load-stations-failure]}}))

(re-frame/reg-event-db
 :load-stations-response
 (fn [db [_ response]]
   (let [stations (->> (js->clj response)
                       (filter :passengerTraffic)
                       (mapv #(assoc % :stationComputedName (str (:stationName %) " (" (:stationShortCode %) ")")))
                       )]
     (assoc db
            :stations stations
            :loading-stations? false))))

(re-frame/reg-event-db
 :load-stations-failure
 (fn [db [_ response]]
   (assoc db
          :stations ["HKI"]
          :loading-stations? false)))

(re-frame/reg-event-fx
 :load-trains
 (fn [{:keys [db]} _]
   {:db (assoc db :loading-trains? true)
    :http-xhrio {:method :get
                 :uri TRAIN_API
                 :params {:station (get-in db [:station :stationShortCode])}
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:load-trains-response]
                 :on-failure [:load-trains-failure]}}))

(re-frame/reg-event-db
 :load-trains-response
 (fn [db [_ response]]
   (assoc db
          :trains (js->clj response)
          :loading-trains? false)))

(re-frame/reg-event-db
 :load-trains-failure
 (fn [db [_ response]]
   (assoc db
          :trains []
          :loading-trains? false)))
