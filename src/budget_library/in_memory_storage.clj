(ns budget-library.in-memory-storage)

(def transactions (atom []))

(defn initialize
  "Initialize in memory storage"
  []
  (swap! transactions (fn [current] [])))

(defn add-transaction
  "Adds transaction to storage"
  [transaction]
  (swap! transactions #(conj % transaction)))

(defn add-partner
  "Adds partner to storage"
  [partner])

(defn get-transactions
  "Retrieves all transactions"
  []
  @transactions)

(defn get-partners
  "Retrieves all partners"
  [])

(defn remove-transaction
  "Removes specified transaction"
  [transaction-id])

(defn remove-partner
  "Removes specified partner"
  [partner-id])
