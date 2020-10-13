(ns budget-library.in-memory-storage
  (:require [budget-library.storage :as s]))

(deftype Storage [transactions]
  s/Storage
  (add-transaction [transaction])
  (add-partner [partner])
  (get-transactions [])
  (get-partners [])
  (remove-transaction [transaction-id])
  (remove-partner [partner-id]))
