(ns budget-library.analysis-test
  (:require [clojure.test :refer :all]
            [budget-library.test-utils :refer :all]
            [budget-library.analysis :as a]))

(deftest net-amount-test
  (testing "Empty list gets 0"
    (is (= 0 (a/net-amount '()))))
  (testing "All negatives get combined negative amount"
    (is (= -3400 (a/net-amount (take 4 daily-transactions)))))
  (testing "Equal amounts positive and negative net at 0"
    (is (= 0 (a/net-amount (take 21 daily-transactions)))))
  (testing "Only positive amounts combine to positive amount"
    (is (= 5500 (a/net-amount (nthrest (take 21 daily-transactions) 10))))))