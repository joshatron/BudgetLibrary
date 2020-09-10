(defproject budget-library "0.1.0-SNAPSHOT"
  :description "Simple budget library"
  :url "https://github.com/joshatron/BudgetLibrary"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot budget-library.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
