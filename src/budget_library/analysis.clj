(ns budget-library.analysis)

(defn net-amount
  "Gets the net amount of money spent/received for the given transactions"
  [transactions]
  (reduce #(+ %1 (:amount %2)) 0 transactions))

(defn total-positive
  "Adds up all the transaction amounts that are positive"
  [transactions]
  (net-amount (filter #(pos? (:amount %)) transactions)))
