(ns application.service.mailer)

(defprotocol mailer
  (send [this recipient message]))
