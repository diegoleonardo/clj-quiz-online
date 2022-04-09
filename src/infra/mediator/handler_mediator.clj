(ns infra.mediator.handler-mediator
  (:require [application.mediator.mediator :as mediator]
            [application.handler.event-handler :as handler]
            [integrant.core :as ig]
            [clojure.spec.alpha :as s]
            [domain.event.domain-event :as domain-event]))

(defn- publish [this event]
  {:pre [(or (s/valid? domain-event/quiz-corrected-schema event)
             (s/valid? domain-event/quiz-submitted-schema event))]}
  (doseq [handler @(:state this)]
    (when (= (:event-name handler) (:name event))
      (handler/handle (:handler handler) event))))

(defrecord handler-mediator [state]
  mediator/mediator

  (register [this handler]
    (swap! (:state this) conj handler))

  (publish [this event]
    (publish this event)))

(defmethod ig/init-key ::mediator
  [_ {:keys [state]}]
  (->handler-mediator state))
