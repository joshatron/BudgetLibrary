(ns budget-library.filtering
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

(defn- beginning-of-year
  "Gets first day of the year"
  [year]
  (t/local-date year))

(defn- end-of-year
  "Gets last day of the year"
  [year]
  (t/minus (t/local-date (+ year 1)) (t/days 1)))

(defn in-year
  "Filters out all transactions that are not in specified year"
  [year transactions]
  (in-dates (beginning-of-year year) (end-of-year year) transactions))

(defn min-amount
  "Filters out all transactions less than the specified amount"
  [amount transactions]
  (seq (filter #(<= amount (:amount %)) transactions)))

(defn positive-only
  "Filters out all transactions with negative amount"
  [transactions]
  (min-amount 0 transactions))

(defn max-amount
  "Filters out all transactions greater than the specified amount"
  [amount transactions]
  (seq (filter #(>= amount (:amount %)) transactions)))

(defn negative-only
  "Filters out all transactions with positive amount"
  [transactions]
  (max-amount 0 transactions))

(defn with-partner
  "Filters out all transactions not with the specified partner"
  [partner transactions]
  (seq (filter #(= partner (:partner %)) transactions)))

(defn- in-list
  "Returns true if element is in the list"
  [list element]
  (some #(= element %) list))

(defn with-partners
  "Filters out all transactions that don't match any partner"
  [partners transactions]
  (seq (filter #(in-list partners (:partner %)) transactions)))

(defn with-tag
  "Filters out all transactions that don't have the tag"
  [tag transactions]
  (seq (filter #(contains? (:tags %) tag) transactions)))

(defn- any-common
  "Returns true if any elements in both list and set"
  [list set]
  (some #(contains? set %) list))

(defn with-any-tags
  "Filters out all transactions that don't have the tags"
  [tags transactions]
  (seq (filter #(any-common tags (:tags %)) transactions)))

(defn- all-common
  "Returns true if all elements in the list are in the set"
  [list set]
  (every? #(contains? set %) list))

(defn with-all-tags
  "Filters out all transactions that don't have all the tags"
  [tags transactions]
  (seq (filter #(all-common tags (:tags %)) transactions)))

(defn get-transaction
  "Finds the transaction with the specified ID"
  [id transactions]
  (first (filter #(= id (:id %)) transactions)))
