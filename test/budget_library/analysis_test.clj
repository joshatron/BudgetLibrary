(ns budget-library.analysis-test
  (:require [clojure.test :refer :all]
            [budget-library.import :as i]
            [budget-library.analysis :as a]
            [java-time :as t])
  (:import (java.util UUID)))

(def repeating-transactions (iterate (fn [v] {:id          (.toString (UUID/randomUUID))
                                              :date        (t/plus (:date v) (t/days 1))
                                              :description (:description v)
                                              :amount      (+ 100 (:amount v))
                                              :partner     (.toString (UUID/randomUUID))
                                              :tags        (:tags v)
                                              })
                                     {:id          (.toString (UUID/randomUUID))
                                      :date        (t/local-date-time 2020 3 30)
                                      :description "Description."
                                      :amount      100
                                      :partner     (.toString (UUID/randomUUID))
                                      :tags        #{:tag1 :tag2 :tag3}}))

(deftest ending-on-test
  (testing "Empty list gets nil"
    (is (nil? (a/ending-on (t/local-date 2020 1 17) '()))))
  )