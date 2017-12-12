(ns clojurescript-train-radiator.views
  (:require [re-frame.core :as re-frame]
            [clojurescript-train-radiator.subs :as subs]
            [cljs-time.core :as time]
            [cljs-time.local :as localtime]
            [clojure.string :as s]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [get-mui-theme color]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]))

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
  (let [station (re-frame/subscribe [:station])
        stations (re-frame/subscribe [:stations])
        trains (re-frame/subscribe [:trains])
        loading-stations? (re-frame/subscribe [:loading-stations?])
        loading-trains? (re-frame/subscribe [:loading-trains?])]
    [:div
     [:h1 "Trains"]
     (if @loading-stations?
       [:p "Loading, please wait..."]
       [:div
        [ui/auto-complete {:hint-text "Choose station"
                           :search-text (:stationComputedName @station)
                           :on-focus #(re-frame/dispatch [:set-station ""])
                           :on-new-request #(re-frame/dispatch [:set-station (js->clj % :keywordize-keys true)])
                           :open-on-focus true
                           :filter (fn [text key] (s/includes? (s/lower-case key) (s/lower-case text)))
                           :dataSource @stations
                           :dataSourceConfig {:text "stationComputedName" :value "stationShortCode"} }]
        (if @loading-trains?
          [:p "Loading, please wait..."]
          [render @trains])])]))

(defn main-panel []
  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme
                {:palette {:text-color "#0bae02"}})}
   [page]])
