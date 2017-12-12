(defproject clojurescript-train-radiator "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.908"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.2"]
                 [day8.re-frame/http-fx "0.1.4"]
                 [cljs-react-material-ui "0.2.48"]
                 [cljsjs/react "15.6.1-1"]
                 [cljsjs/react-dom "15.6.1-1"]
                 [cljs-ajax "0.7.3"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]]

  :plugins [[lein-cljsbuild "1.1.5"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.4"]
                   [figwheel-sidecar "0.5.13"]
                   [com.cemerick/piggieback "0.2.1"]]

   :plugins      [[lein-figwheel "0.5.13"]]}}

:cljsbuild
{:builds
 [{:id           "dev"
   :source-paths ["src/cljs"]
   :figwheel     {:on-jsload "clojurescript-train-radiator.core/mount-root"}
   :compiler     {:main                 clojurescript-train-radiator.core
                  :output-to            "resources/public/js/compiled/app.js"
                  :output-dir           "resources/public/js/compiled/out"
                  :asset-path           "js/compiled/out"
                  :source-map-timestamp true
                  :preloads             [devtools.preload]
                  :external-config      {:devtools/config {:features-to-install :all}}
                  }}

  {:id           "min"
   :source-paths ["src/cljs"]
   :compiler     {:main            clojurescript-train-radiator.core
                  :output-to       "resources/public/js/compiled/app.js"
                  :optimizations   :advanced
                  :closure-defines {goog.DEBUG false}
                  :pretty-print    false}}


  ]})
