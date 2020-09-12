(ns budget-library.import
  (:import (java.util UUID)))

(defn create-transaction
  "Creates transaction based on arguments"
  [description cents partner tags]
  {:pre [(string? description)
         (integer? cents)
         (string? partner)
         (not (empty? partner))
         (set? tags)
         (every? keyword? tags)]}
  {
   :id          (.toString (UUID/randomUUID))
   :description description
   :amount      cents
   :partner     partner
   :tags        tags
   })