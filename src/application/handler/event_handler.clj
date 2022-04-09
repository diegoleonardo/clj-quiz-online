(ns application.handler.event-handler)

(defprotocol handler
  (handle [this event]))
