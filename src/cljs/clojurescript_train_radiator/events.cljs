(ns clojurescript-train-radiator.events
  (:require [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]))

;;
;; Accessing the API
;;

(def TRAIN_API "https://rata.digitraffic.fi/api/v1/live-trains")

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   {}))

(re-frame/reg-event-fx
 :load-trains
 (fn [{:keys [db]} _]
   {:http-xhrio {:method :get
                 :uri TRAIN_API
                 :params {:station "HKI"}
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:load-trains-response]
                 :on-failure [:load-trains-failure]}}))

(re-frame/reg-event-db
 :load-trains-response
 (fn [db [_ response]]
   (assoc db :trains (js->clj response))))

(re-frame/reg-event-db
 :load-trains-failure
 (fn [db [_ response]]
   (assoc db :trains [])))
