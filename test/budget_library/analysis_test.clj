(ns budget-library.analysis-test
  (:require [clojure.test :refer :all]
            [budget-library.import :as i]
            [budget-library.analysis :as a]
            [java-time :as t]))

(deftest ending-on-test
  (testing "Empty list gets nil"
    (is (nil? (a/ending-on (t/local-date 2020 1 17) '()))))
  )