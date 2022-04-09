(ns infra.service.mailer-memory
  (:require [application.service.mailer :as mailer]
            [integrant.core :as ig]))

(defrecord mailer-memory [state]
  mailer/mailer

  (send [this recipient message]
    (swap! (:state this) conj {:recipient recipient
                               :message   message})))

(defmethod ig/init-key ::mailer
  [_ {:keys [state]}]
  (->mailer-memory {:state (atom state)}))
