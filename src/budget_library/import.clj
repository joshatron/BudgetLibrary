(ns budget-library.import
  (:import (java.util UUID)))

(defn create-transaction
  "Creates transaction based on arguments"
  [description cents partner tags]
  {
   :id          (.toString (UUID/randomUUID))
   :description description
   :amount      cents
   :partner     partner
   :tags        tags
   })