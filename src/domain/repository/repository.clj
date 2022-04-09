(ns domain.repository.repository)

(defprotocol repository
  (get-by-id [this id]))
