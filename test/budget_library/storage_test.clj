(ns budget-library.storage-test
  (:require [clojure.test :refer :all]
            [budget-library.test-utils :refer :all]
            [budget-library.import :as i]
            [budget-library.in-memory-storage :as s]))

(defn initialize-test
  "Initialize the environment for the test"
  []
  (s/initialize))

(deftest add-transaction-test
  (testing "Add one transaction"
    (initialize-test)
    (is (empty? (s/get-transactions)))
    (s/add-transaction (first daily-transactions))
    (is (= (take 1 daily-transactions) (seq (s/get-transactions)))))
  (testing "Add multiple transactions"
    (initialize-test)
    (is (empty? (s/get-transactions)))
    (s/add-transaction (first daily-transactions))
    (s/add-transaction (first (rest daily-transactions)))
    (is (= (take 2 daily-transactions) (seq (s/get-transactions))))))

(deftest add-partner-test
  (testing "Add one partner"
    (initialize-test)
    (is (empty? (s/get-partners)))
    (s/add-partner (first infinite-partners))
    (is (= (take 1 infinite-partners) (seq (s/get-partners)))))
  (testing "Add multiple partners"
    (initialize-test)
    (is (empty? (s/get-partners)))
    (s/add-partner (first infinite-partners))
    (s/add-partner (first (rest infinite-partners)))
    (is (= (take 2 infinite-partners) (seq (s/get-partners))))))

(deftest remove-transaction-test
  (testing "Remove existing transaction"
    (initialize-test)
    (s/add-transaction (first daily-transactions))
    (s/add-transaction (first (rest daily-transactions)))
    (s/add-transaction (first (rest (rest daily-transactions))))
    (s/remove-transaction (:id (first (rest daily-transactions))))
    (is (= 2 (count (s/get-transactions))))
    (is (= (first daily-transactions) (first (s/get-transactions))))
    (is (= (first (rest (rest daily-transactions))) (first (rest (s/get-transactions))))))
  (testing "Remove nonexistant transaction"
    (initialize-test)
    (s/add-transaction (first daily-transactions))
    (s/add-transaction (first (rest daily-transactions)))
    (s/remove-transaction "NOT-AN-ID")
    (is (= 2 (count (s/get-transactions))))
    (is (= (take 2 daily-transactions) (s/get-transactions)))))

(deftest remove-partner-test
  (testing "Remove existing partner"
    (initialize-test)
    (s/add-partner (first infinite-partners))
    (s/add-partner (first (rest infinite-partners)))
    (s/add-partner (first (rest (rest infinite-partners))))
    (s/remove-partner (:id (first infinite-partners)))
    (is (= (take 2 (rest infinite-partners)) (s/get-partners))))
  (testing "Remove nonexistant partner"
    (initialize-test)
    (s/add-partner (first infinite-partners))
    (s/add-partner (first (rest infinite-partners)))
    (s/remove-partner "NOT-AN-ID")
    (is (= (take 2 infinite-partners) (s/get-partners)))))
