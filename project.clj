(defproject indolent "0.1.0-SNAPSHOT"
  :description "Restful JSON HTTP client"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/core.incubator "0.1.0"]
                 [clj-http "0.3.5"]
                 [slingshot "0.10.2"]
                 [cheshire "4.0.0"]]
  :profiles
  {:dev {:dependencies [[clj-http-fake "0.3.0"]
                        [ring/ring-core "1.1.0-RC1"]]}})
