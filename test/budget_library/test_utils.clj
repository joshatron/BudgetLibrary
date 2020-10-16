(ns budget-library.test-utils
  (:require [java-time :as t]
            [clojure.string :as str])
  (:import (java.util UUID)))

(defn- next-tag [tag]
  (keyword (str "tag" (inc (Integer/parseInt (subs (name tag) 3))))))

(defn- next-tags [tags]
  (set (map next-tag tags)))

(def daily-transactions (iterate (fn [v] {:id          (.toString (UUID/randomUUID))
                                          :date        (t/plus (:date v) (t/days 1))
                                          :description (:description v)
                                          :amount      (+ 100 (:amount v))
                                          :partner     (str (inc (Integer/parseInt (:partner v))))
                                          :tags        (next-tags (:tags v))
                                          })
                                 {:id          (.toString (UUID/randomUUID))
                                  :date        (t/local-date-time 2020 3 30)
                                  :description "Description."
                                  :amount      -1000
                                  :partner     "1"
                                  :tags        #{:tag1 :tag2}}))

(defn- next-num
  "Gets next number for infinite partners"
  [current]
  (str (inc (Integer/parseInt (last (str/split current #" "))))))

(def infinite-partners (iterate (fn [v] {:id (.toString (UUID/randomUUID))
                                         :name (str "Partner " (next-num (:name v)))
                                         :description (str "Description " (next-num (:description v)))})
                                {:id (.toString (UUID/randomUUID))
                                 :name "Partner 1"
                                 :description "Description 1"}))