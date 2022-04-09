(ns application.mediator.mediator)

(defprotocol mediator
  (register [this handler])
  (publish [this event]))
