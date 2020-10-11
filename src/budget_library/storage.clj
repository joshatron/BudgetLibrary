(ns budget-library.storage)

(defprotocol Storage
  "Defined behavior for storing transactions and partners"
  (add-transaction [transaction] "Adds transaction to storage")
  (add-partner [partner] "Adds partner to storage")
  (get-transactions [] "Retrieves all transactions")
  (get-partners [] "Retrieves all partners")
  (remove-transaction [transaction-id] "Removes specified transaction")
  (remove-partner [partner-id] "Removes specified partner"))
