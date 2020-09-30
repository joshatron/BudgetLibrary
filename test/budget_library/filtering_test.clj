(ns budget-library.filtering-test
  (:require [clojure.test :refer :all]
            [budget-library.import :as i]
            [budget-library.filtering :as f]
            [java-time :as t])
  (:import (java.util UUID)))

(def daily-transactions (iterate (fn [v] {:id          (.toString (UUID/randomUUID))
                                              :date        (t/plus (:date v) (t/days 1))
                                              :description (:description v)
                                              :amount      (+ 100 (:amount v))
                                              :partner     (str (inc (Integer/parseInt (:partner v))))
                                              :tags        (:tags v)
                                              })
                                     {:id          (.toString (UUID/randomUUID))
                                      :date        (t/local-date-time 2020 3 30)
                                      :description "Description."
                                      :amount      -1000
                                      :partner     "1"
                                      :tags        #{:tag1 :tag2 :tag3}}))

(deftest ending-on-test
  (testing "Empty list gets nil"
    (is (nil? (f/ending-on (t/local-date 2020 1 17) '()))))
  (testing "All before date returns same sequence"
    (is (= (take 3 daily-transactions) (f/ending-on (t/local-date 2020 5 1) (take 3 daily-transactions)))))
  (testing "Returns only ones before or on date"
    (is (= (take 2 daily-transactions) (f/ending-on (t/local-date 2020 3 31) (take 3 daily-transactions)))))
  (testing "All after returns nil"
    (is (nil? (f/ending-on (t/local-date 2020 1 1) (take 10 daily-transactions))))))

(deftest starting-on-test
  (testing "Empty list gets nil"
    (is (nil? (f/starting-on (t/local-date 2020 1 1) '()))))
  (testing "All after date returns same sequence"
    (is (= (take 3 daily-transactions) (f/starting-on (t/local-date 2020 1 16) (take 3 daily-transactions)))))
  (testing "Returns only ones on or after date"
    (is (= (rest (take 3 daily-transactions)) (f/starting-on (t/local-date 2020 3 31) (take 3 daily-transactions)))))
  (testing "All before returns nil"
    (is (nil? (f/starting-on (t/local-date 2020 4 30) (take 10 daily-transactions))))))

(deftest in-dates-test
  (testing "Empty list gets nil"
    (is (nil? (f/in-dates (t/local-date 2020 1 1) (t/local-date 2021 1 1) '()))))
  (testing "All in date range returns same sequence"
    (is (= (take 5 daily-transactions) (f/in-dates (t/local-date 2020 1 1) (t/local-date 2021 1 1) (take 5 daily-transactions)))))
  (testing "Returns only ones in date range"
    (is (= (rest (take 4 daily-transactions)) (f/in-dates (t/local-date 2020 3 31) (t/local-date 2020 4 2) (take 5 daily-transactions)))))
  (testing "All outside returns nil"
    (is (nil? (f/in-dates (t/local-date 2020 4 30) (t/local-date 2020 5 30) (take 10 daily-transactions))))))

(deftest in-month-test
  (testing "Empty list gets nil"
    (is (nil? (f/in-month 2020 1 '()))))
  (testing "All in range returns same sequence"
    (is (= (rest (rest (take 7 daily-transactions))) (f/in-month 2020 4 (rest (rest (take 7 daily-transactions)))))))
  (testing "Only ones in range returned as sequence"
    (is (= (rest (rest (take 32 daily-transactions))) (f/in-month 2020 4 (take 50 daily-transactions)))))
  (testing "All outside returns nil"
    (is (nil? (f/in-month 2020 7 (take 10 daily-transactions))))))

(deftest in-year-test
  (testing "Empty list gets nil"
    (is (nil? (f/in-year 2020 '()))))
  (testing "All in range returns same sequence"
    (is (= (take 15 daily-transactions) (f/in-year 2020 (take 15 daily-transactions)))))
  (testing "Only ones in range returned as sequence"
    (is (= (nthrest (take 642 daily-transactions) 277) (f/in-year 2021 (take 800 daily-transactions)))))
  (testing "All outside returns nil"
    (is (nil? (f/in-year 2025 (take 1000 daily-transactions))))))

(deftest min-amount-test
  (testing "Empty list gets nil"
    (is (nil? (f/min-amount 0 '()))))
  (testing "All over amount returns same sequence"
    (is (= (take 10 daily-transactions) (f/min-amount -1000 (take 10 daily-transactions)))))
  (testing "Only returns ones over and equal to amount"
    (is (= (nthrest (take 10 daily-transactions) 5) (f/min-amount -500 (take 10 daily-transactions)))))
  (testing "No amounts high enough returns nil"
    (is (nil? (f/min-amount 1000 (take 15 daily-transactions))))))

(deftest max-amount-test
  (testing "Empty list gets nil"
    (is (nil? (f/max-amount 0 '()))))
  (testing "All under amount return same sequence"
    (is (= (take 11 daily-transactions) (f/max-amount 0 (take 11 daily-transactions)))))
  (testing "Only ones less than or equal to amount are returned"
    (is (= (take 11 daily-transactions) (f/max-amount 0 (take 100 daily-transactions)))))
  (testing "None matching returns nil"
    (is (nil? (f/max-amount -10000 (take 5000 daily-transactions))))))

(deftest positive-only-test
  (testing "Empty list gets nil"
    (is (nil? (f/positive-only '()))))
  (testing "All positive returns same sequence"
    (is (= (nthrest (take 50 daily-transactions) 10) (f/positive-only (nthrest (take 50 daily-transactions) 10)))))
  (testing "Only returns ones that the amount is 0 or greater"
    (is (= (nthrest (take 50 daily-transactions) 10) (f/positive-only (take 50 daily-transactions)))))
  (testing "None matching returns nil"
    (is (nil? (f/positive-only (take 5 daily-transactions))))))

(deftest negative-only-test
  (testing "Empty list gets nil"
    (is (nil? (f/negative-only '()))))
  (testing "All negative returns same sequence"
    (is (= (take 10 daily-transactions) (f/negative-only (take 10 daily-transactions)))))
  (testing "Only amount of 0 or less gets returned"
    (is (= (take 11 daily-transactions) (f/negative-only (take 1000 daily-transactions)))))
  (testing "All positive returns nil"
    (is (nil? (f/negative-only (nthrest (take 100 daily-transactions) 11))))))

(deftest with-partner-test
  (testing "Empty list gets nil"
    (is (nil? (f/with-partner "1" '()))))
  (testing "Only with partner returns same sequence"
    (is (= (take 10 (repeat (first daily-transactions))) (f/with-partner "1" (take 10 (repeat (first daily-transactions)))))))
  (testing "Only return transactions with partner"
    (is (= (nthrest (take 3 daily-transactions) 2) (f/with-partner "3" (take 100 daily-transactions)))))
  (testing "None match return nil"
    (is (nil? (f/with-partner "-1" (take 1000 daily-transactions))))))

(deftest with-partners-test
  (testing "Empty list gets nil"
    (is (nil? (f/with-partners ["1" "2"] '()))))
  (testing "All have matching partner returns same sequence"
    (is (= (take 3 daily-transactions) (f/with-partners ["1" "2" "3"] (take 3 daily-transactions)))))
  (testing "Only matching are returned"
    (is (= (take 3 daily-transactions) (f/with-partners ["1" "2" "3"] (take 100 daily-transactions)))))
  (testing "Empty partners returns nil"
    (is (nil? (f/with-partners [] (take 1000 daily-transactions)))))
  (testing "None match returns nil"
    (is (nil? (f/with-partners ["-1" "-2"] (take 100 daily-transactions))))))
