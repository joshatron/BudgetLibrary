(ns budget-library.analysis)

(defn net-amount
  "Gets the net amount of money spent/received for the given transactions"
  [transactions]
  (reduce #(+ %1 (:amount %2)) 0 transactions))
