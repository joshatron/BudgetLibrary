(ns budget-library.import-test
  (:require [clojure.test :refer :all]
            [budget-library.import :as i]
            [java-time :as t]))

(deftest create-transaction-result
  (testing "ID created"
    (is (:id (i/create-transaction "Test description." (t/local-date-time 2020 9 14) 1586 "SOME-UUID" #{:entertainment :movie}))))
  (testing "ID is string"
    (is (string? (:id (i/create-transaction "Test description." (t/local-date-time 2020 9 14) 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "ID not empty"
    (is (not= "" (:id (i/create-transaction "Test description." (t/local-date-time 2020 9 14) 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "ID unique"
    (is (not= (:id (i/create-transaction "Test description." (t/local-date-time 2020 9 14) 1586 "SOME-UUID" #{:entertainment :movie}))
              (:id (i/create-transaction "Test description." (t/local-date-time 2020 9 14) 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Description matches passed"
    (is (= "Test description." (:description (i/create-transaction "Test description." (t/local-date-time 2020 9 14) 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Description matches passed"
    (is (= (t/local-date-time 2020 9 14) (:date (i/create-transaction "Test description." (t/local-date-time 2020 9 14) 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Positive amount matches passed"
    (is (= 1586 (:amount (i/create-transaction "Test description." (t/local-date-time 2020 9 14) 1586 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Negative amount matches passed"
    (is (= -1600 (:amount (i/create-transaction "Test description." (t/local-date-time 2020 9 14) -1600 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Partner matches passed"
    (is (= "SOME-UUID" (:partner (i/create-transaction "Test description." (t/local-date-time 2020 9 14) -1600 "SOME-UUID" #{:entertainment :movie})))))
  (testing "Tags match passed"
    (is (= #{:movie :entertainment} (:tags (i/create-transaction "Test description." (t/local-date-time 2020 9 14) 1586 "SOME-UUID" #{:entertainment :movie}))))))

(deftest create-transaction-bad-input
  (testing "Description not string"
    (is (thrown? AssertionError (i/create-transaction 1234 (t/local-date-time 2020 9 14) 6000 "SOME-UUID" #{:tag1 :tag2}))))
  (testing "Date not local date time"
    (is (thrown? AssertionError (i/create-transaction "Description" "9/14/2020" 6000 "SOME-UUID" #{:tag1 :tag2}))))
  (testing "Amount not int"
    (is (thrown? AssertionError (i/create-transaction "Description" (t/local-date-time 2020 9 14) 6000. "SOME-UUID" #{:tag1 :tag2}))))
  (testing "Partner not string"
  (testing "Amount not int"
    (is (thrown? AssertionError (i/create-transaction "Description" (t/local-date-time 2020 9 14) 6000. "SOME-UUID" #{:tag1 :tag2}))))
  (testing "Partner not string"
    (is (thrown? AssertionError (i/create-transaction "Description" (t/local-date-time 2020 9 14) 6000 :partner-keyword #{:tag1 :tag2}))))
  (testing "Partner empty"
    (is (thrown? AssertionError (i/create-transaction "Description" (t/local-date-time 2020 9 14) 6000 "" #{:tag1 :tag2}))))
  (testing "Tags not set"
    (is (thrown? AssertionError (i/create-transaction "Description" (t/local-date-time 2020 9 14) 6000 "SOME-UUID" [:tag1 :tag2]))))
  (testing "Tags not all keywords"
    (is (thrown? AssertionError (i/create-transaction "Description" (t/local-date-time 2020 9 14) 6000 "SOME-UUID" #{:tag1 "tag2"}))))))

(deftest create-partner-result
  (testing "ID created"
    (is (:id (i/create-partner "Partner Name" "Description."))))
  (testing "ID is string"
    (is (string? (:id (i/create-partner "Partner Name" "Description")))))
  (testing "ID not empty"
    (is (not= "" (:id (i/create-partner "Partner Name" "Description.")))))
  (testing "ID unique"
    (is (not= (:id (i/create-partner "Partner Name" "Description."))
              (:id (i/create-partner "Partner Name" "Description.")))))
  (testing "Name matches passed"
    (is (= "Partner Name" (:name (i/create-partner "Partner Name", "Description.")))))
  (testing "Description matches passed"
    (is (= "Description." (:description (i/create-partner "Partner Name", "Description."))))))

(deftest create-partner-bad-input
  (testing "Name not string"
    (is (thrown? AssertionError (i/create-partner 6000 "Description"))))
  (testing "Name empty"
    (is (thrown? AssertionError (i/create-partner "" "Description"))))
  (testing "Description not string"
    (is (thrown? AssertionError (i/create-partner "Partner Name" 100.))))
  )
