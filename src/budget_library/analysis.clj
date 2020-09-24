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

(defn in-dates
  "Filters out all transactions that are not in the date range inclusive"
  [start end transactions]
  (seq (filter #(and (on-or-before (:date %) end) (on-or-after (:date %) start)) transactions)))

(defn- beginning-of-month
  "Gets the first day of a month"
  [year month]
  (t/local-date year month))

(defn- end-of-month
  "Gets the last day of a month"
  [year month]
  (t/minus (t/plus (t/local-date year month) (t/months 1)) (t/days 1)))

(defn in-month
  "Filters out all transactions that are not in specified month"
  [year month transactions]
  (in-dates (beginning-of-month year month) (end-of-month year month) transactions))
