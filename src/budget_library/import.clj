(ns budget-library.import
  (:require [java-time :as t])
  (:import (java.util UUID)
           (java.time LocalDateTime)))

(defn create-transaction
  "Creates transaction based on arguments"
  ([description cents partner tags]
   (create-transaction description (t/local-date-time) cents partner tags))
  ([description year month day cents partner tags]
   (create-transaction description (t/local-date-time year month day) cents partner tags))
  ([description date cents partner tags]
  {:pre [(string? description)
         (instance? LocalDateTime date)
         (integer? cents)
         (string? partner)
         (not (empty? partner))
         (set? tags)
         (every? keyword? tags)]}
  {
   :id          (.toString (UUID/randomUUID))
   :date        date
   :description description
   :amount      cents
   :partner     partner
   :tags        tags
   }))

(defn create-partner
  "Creates partner based on arguments"
  [name description]
  {:pre [(string? name)
         (not (empty? name))
         (string? description)]}
  {
   :id (.toString (UUID/randomUUID))
   :name name
   :description description
   })