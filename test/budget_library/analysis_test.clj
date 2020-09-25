(ns budget-library.analysis-test
  (:require [clojure.test :refer :all]
            [budget-library.import :as i]
            [budget-library.analysis :as a]
            [java-time :as t])
  (:import (java.util UUID)))

(def daily-transactions (iterate (fn [v] {:id          (.toString (UUID/randomUUID))
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
  (testing "All before date returns same sequence"
    (is (= (take 3 daily-transactions) (a/ending-on (t/local-date 2020 5 1) (take 3 daily-transactions)))))
  (testing "Returns only ones before or on date"
    (is (= (take 2 daily-transactions) (a/ending-on (t/local-date 2020 3 31) (take 3 daily-transactions)))))
  (testing "All after returns nil"
    (is (nil? (a/ending-on (t/local-date 2020 1 1) (take 10 daily-transactions))))))

(deftest starting-on-test
  (testing "Empty list gets nil"
    (is (nil? (a/starting-on (t/local-date 2020 1 1) '()))))
  (testing "All after date returns same sequence"
    (is (= (take 3 daily-transactions) (a/starting-on (t/local-date 2020 1 16) (take 3 daily-transactions)))))
  (testing "Returns only ones on or after date"
    (is (= (rest (take 3 daily-transactions)) (a/starting-on (t/local-date 2020 3 31) (take 3 daily-transactions)))))
  (testing "All before returns nil"
    (is (nil? (a/starting-on (t/local-date 2020 4 30) (take 10 daily-transactions))))))

(deftest in-dates-test
  (testing "Empty list gets nil"
    (is (nil? (a/in-dates (t/local-date 2020 1 1) (t/local-date 2021 1 1) '()))))
  (testing "All in date range returns same sequence"
    (is (= (take 5 daily-transactions) (a/in-dates (t/local-date 2020 1 1) (t/local-date 2021 1 1) (take 5 daily-transactions)))))
  (testing "Returns only ones in date range"
    (is (= (rest (take 4 daily-transactions)) (a/in-dates (t/local-date 2020 3 31) (t/local-date 2020 4 2) (take 5 daily-transactions)))))
  (testing "All outside returns nil"
    (is (nil? (a/in-dates (t/local-date 2020 4 30) (t/local-date 2020 5 30) (take 10 daily-transactions))))))

(deftest in-month-test
  (testing "Empty list gets nil"
    (is (nil? (a/in-month 2020 1 '()))))
  (testing "All in range returns same sequence"
    (is (= (rest (rest (take 7 daily-transactions))) (a/in-month 2020 4 (rest (rest (take 7 daily-transactions)))))))
  (testing "Only ones in range returned as sequence"
    (is (= (rest (rest (take 32 daily-transactions))) (a/in-month 2020 4 (take 50 daily-transactions)))))
  (testing "All outside returns nil"
    (is (nil? (a/in-month 2020 7 (take 10 daily-transactions))))))

(deftest in-year-test
  (testing "Empty list gets nil"
    (is (nil? (a/in-year 2020 '()))))
  (testing "All in range returns same sequence"
    (is (= (take 15 daily-transactions) (a/in-year 2020 (take 15 daily-transactions)))))
  (testing "Only ones in range returned as sequence"
    (is (= (nthrest (take 642 daily-transactions) 277) (a/in-year 2021 (take 800 daily-transactions)))))
  (testing "All outside returns nil"
    (is (nil? (a/in-year 2025 (take 1000 daily-transactions)))))
  )
