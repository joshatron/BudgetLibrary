(ns budget-library.import-test
  (:require [clojure.test :refer :all]
            [budget-library.import :as i]))

(deftest basic-create-transaction
  (testing "ID created"
    (is (:id (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie}))))
  (testing "ID is string"
    (is (string? (:id (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "ID not empty"
    (is (not= "" (:id (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "ID unique"
    (is (not= (:id (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie}))
              (:id (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Description matches passed"
    (is (= "Test description." (:description (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Positive amount matches passed"
    (is (= 1586 (:amount (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Negative amount matches passed"
    (is (= -1600 (:amount (i/create-transaction "Test description." -1600 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Partner matches passed"
    (is (= "SOME-UUID" (:partner (i/create-transaction "Test description." -1600 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Tags match passed"
    (is (= #{:movie :entertainment} (:tags (i/create-transaction "Test description." 1586 "SOME-UUID" #{:entertainment :movie}))))))

(deftest create-transaction-bad-input
  (testing "Description not string"
    (is (thrown? AssertionError (i/create-transaction 1234 6000 "SOME-UUID" #{:tag1 :tag2}))))
  (testing "Amount not int"
    (is (thrown? AssertionError (i/create-transaction "Description" 6000. "SOME-UUID" #{:tag1 :tag2}))))
  (testing "Partner not string"
    (is (thrown? AssertionError (i/create-transaction "Description" 6000 :partner-keyword #{:tag1 :tag2}))))
  (testing "Partner empty"
    (is (thrown? AssertionError (i/create-transaction "Description" 6000 "" #{:tag1 :tag2}))))
  (testing "Tags not set"
    (is (thrown? AssertionError (i/create-transaction "Description" 6000 "SOME-UUID" [:tag1 :tag2]))))
  (testing "Tags not all keywords"
    (is (thrown? AssertionError (i/create-transaction "Description" 6000 "SOME-UUID" #{:tag1 "tag2"})))))
