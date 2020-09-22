(ns budget-library.analysis
  (:require [java-time :as t]))

(defn- on-or-before
  "Checks that the date time is on or before the date"
  [date-time date]
  (t/after? date (t/minus (t/local-date date-time) (t/days 1))))

(defn ending-on
  "Filters out all transactions that happen after specified date"
  [date transactions]
  (seq (filter #(on-or-before (:date %) date) transactions)))

(defn- on-or-after
  "Checks that the date time is on or after the date"
  [date-time date]
  (t/before? date (t/plus (t/local-date date-time) (t/days 1))))

(defn starting-on
  "Filters out all transactions that happen before specified date"
  [date transactions]
  (seq (filter #(on-or-after (:date %) date) transactions)))
