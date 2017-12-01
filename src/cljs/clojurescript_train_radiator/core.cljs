(ns clojurescript-train-radiator.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [clojurescript-train-radiator.events :as events]
            [clojurescript-train-radiator.views :as views]
            [clojurescript-train-radiator.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (re-frame/dispatch [:load-trains])
  (dev-setup)
  (mount-root))
