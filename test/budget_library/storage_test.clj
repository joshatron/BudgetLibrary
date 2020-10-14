(ns budget-library.storage-test
  (:require [clojure.test :refer :all]
            [budget-library.test-utils :refer :all]
            [budget-library.import :as i]
            [budget-library.in-memory-storage :as s]))

(deftest add-transaction-test
  (testing "Basic add"
    (s/initialize)
    (is (empty? (s/get-transactions)))
    (s/add-transaction (first daily-transactions))
    (is (= (take 1 daily-transactions) (seq (s/get-transactions)))))
  (testing "Add multiple"
    (s/initialize)
    (is (empty? (s/get-transactions)))
    (s/add-transaction (first daily-transactions))
    (s/add-transaction (first (rest daily-transactions)))
    (is (= (take 2 daily-transactions) (seq (s/get-transactions)))))
  )
