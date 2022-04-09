(ns infra.mediator.handler-mediator
  (:require [application.mediator.mediator :as mediator]
            [application.handler.event-handler :as handler]
            [integrant.core :as ig]))

(defrecord handler-mediator [state]
  mediator/mediator

  (register [this handler]
    (swap! (:state this) conj handler))

  (publish [this event]
    (doseq [handler @(:state this)]
      (when (= (:event-name handler) (:name event))
        (handler/handle (:handler handler) event)))))

(defmethod ig/init-key ::mediator
  [_ {:keys [state]}]
  (->handler-mediator state))
