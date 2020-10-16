(ns budget-library.storage-test
  (:require [clojure.test :refer :all]
            [budget-library.test-utils :refer :all]
            [budget-library.import :as i]
            [budget-library.in-memory-storage :as s]))

(deftest add-transaction-test
  (testing "Add one transaction"
    (s/initialize)
    (is (empty? (s/get-transactions)))
    (s/add-transaction (first daily-transactions))
    (is (= (take 1 daily-transactions) (seq (s/get-transactions)))))
  (testing "Add multiple transactions"
    (s/initialize)
    (is (empty? (s/get-transactions)))
    (s/add-transaction (first daily-transactions))
    (s/add-transaction (first (rest daily-transactions)))
    (is (= (take 2 daily-transactions) (seq (s/get-transactions))))))

(deftest add-partner-test
  (testing "Add one partner"
    (s/initialize)
    (is (empty? (s/get-partners)))
    (s/add-partner (first infinite-partners))
    (is (= (take 1 infinite-partners) (seq (s/get-partners)))))
  (testing "Add multiple partners"
    (s/initialize)
    (is (empty? (s/get-partners)))
    (s/add-partner (first infinite-partners))
    (s/add-partner (first (rest infinite-partners)))
    (is (= (take 2 infinite-partners) (seq (s/get-partners))))))

