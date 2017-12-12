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
        trains (re-frame/subscribe [:trains])
        loading? (re-frame/subscribe [:loading?])]
    [:div
     [:h1 "Trains"]
     [ui/auto-complete {:hint-text "Choose station"
                        :search-text @station
                        :on-focus #(re-frame/dispatch [:set-station ""])
                        :on-update-input #(re-frame/dispatch [:set-station (s/upper-case %)])
                        :on-close #(re-frame/dispatch [:load-trains])
                        :open-on-focus true
                        :filter (fn [text key] (not= -1 (.indexOf key text)))
                        :dataSource (clj->js ["HKI" "TKU" "TPE"])}]
     (if @loading?
       [:p "Loading, please wait..."]
       [render @trains])]))

(defn main-panel []
  [ui/mui-theme-provider
   {:mui-theme (get-mui-theme
                {:palette {:text-color "#0bae02"}})}
   [page]])
