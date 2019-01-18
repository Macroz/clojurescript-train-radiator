(defproject clojurescript-train-radiator "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [reagent "0.8.1"]
                 [re-frame "0.10.6"]
                 [cljs-ajax "0.8.0"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]]

  :plugins [[lein-cljsbuild "1.1.5"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.10"]
                   [figwheel-sidecar "0.5.18"]
                   [com.cemerick/piggieback "0.2.2"]]

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
