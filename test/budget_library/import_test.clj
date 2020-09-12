(ns budget-library.import-test
  (:require [clojure.test :refer :all]
            [budget-library.import :as i]))

(deftest basic-create-transaction
  (testing "Tests ID created"
    (is (:id (i/create-transaction "Test description." 1586 "SOME-UUID" [:entertainment :movie]))))
  (testing "Tests ID is string"
    (is (string? (:id (i/create-transaction "Test description." 1586 "SOME-UUID" [:entertainment :movie])))))
  (testing "Tests ID not empty"
    (is (not= "" (:id (i/create-transaction "Test description." 1586 "SOME-UUID" [:entertainment :movie])))))
  (testing "Tests ID unique"
    (is (not= (:id (i/create-transaction "Test description." 1586 "SOME-UUID" [:entertainment :movie]))
              (:id (i/create-transaction "Test description." 1586 "SOME-UUID" [:entertainment :movie])))))
  (testing "Tests description"
    (is (= "Test description." (:description (i/create-transaction "Test description." 1586 "SOME-UUID" [:entertainment :movie])))))
  (testing "Tests positive amount"
    (is (= 1586 (:amount (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Tests negative amount"
    (is (= -1600 (:amount (i/create-transaction "Test description." -1600 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Tests partner"
    (is (= "SOME-UUID" (:partner (i/create-transaction "Test description." -1600 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Tests tags"
    (is (= #{:movie :entertainment} (:tags (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie}))))))
