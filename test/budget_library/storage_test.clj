(ns budget-library.storage-test
  (:require [clojure.test :refer :all]
            [budget-library.test-utils :refer :all]
            [budget-library.import :as i]
            [budget-library.in-memory-storage :as s]))

(def store (s/->Storage []))

