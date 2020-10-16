(ns budget-library.in-memory-storage)

(def ^:private transactions (atom []))
(def ^:private partners (atom []))

(defn initialize
  "Initialize in memory storage"
  []
  (swap! transactions (fn [current] []))
  (swap! partners (fn [current] [])))

(defn add-transaction
  "Adds transaction to storage"
  [transaction]
  (swap! transactions #(conj % transaction)))

(defn add-partner
  "Adds partner to storage"
  [partner]
  (swap! partners #(conj % partner)))

(defn get-transactions
  "Retrieves all transactions"
  []
  @transactions)

(defn get-partners
  "Retrieves all partners"
  []
  @partners)

(defn remove-transaction
  "Removes specified transaction"
  [transaction-id])

(defn remove-partner
  "Removes specified partner"
  [partner-id])
