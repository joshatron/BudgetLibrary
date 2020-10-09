(defproject budget-library "0.1.0-SNAPSHOT"
  :description "Simple budget library"
  :url "https://github.com/joshatron/BudgetLibrary"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clojure.java-time "0.3.2"]
                 [compojure "1.6.1"]
                 [http-kit "2.3.0"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot budget-library.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
