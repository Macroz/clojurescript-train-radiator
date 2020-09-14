(defproject clojurescript-train-radiator "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773"]
                 [reagent "0.10.0"]
                 [re-frame "1.1.1"]
                 [cljs-ajax "0.8.1"]
                 [compojure "1.6.2"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [yogthos/config "1.1.7"]
                 [ring "1.8.1"]]

  :plugins [[lein-cljsbuild "1.1.5"]]

  :min-lein-version "2.9.1"

  :source-paths ["src/clj"]
  :resource-paths ["resources" "target/cljsbuild"]
  :target-path "target/%s/"
  :clean-targets ["target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :main clojurescript-train-radiator.server

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "1.0.2"]
                   [figwheel-sidecar "0.5.20"]
                   [cider/piggieback "0.5.1"]]
    :plugins [[lein-figwheel "0.5.20"]
              [lein-ancient "0.6.15"]]}}

  :cljsbuild
  {:builds
   [{:id "dev"
     :source-paths ["src/cljs"]
     :figwheel {:on-jsload "clojurescript-train-radiator.core/mount-root"}
     :compiler {:main clojurescript-train-radiator.core
                :output-to "target/cljsbuild/public/js/app.js"
                :output-dir "target/cljsbuild/public/js/out"
                :asset-path "js/out"
                :source-map-timestamp true
                :preloads [devtools.preload]
                :external-config {:devtools/config {:features-to-install :all}}}}]})
