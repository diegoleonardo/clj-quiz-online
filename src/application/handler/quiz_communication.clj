(ns application.handler.quiz-communication
  (:require [application.handler.event-handler :as event-handler]
            [application.service.mailer :as mailer]
            [integrant.core :as ig]
            [domain.event.domain-event :as domain-event]
            [clojure.spec.alpha :as s]))

(defn- execute [this event]
  {:pre [(s/valid? domain-event/quiz-corrected-schema event)]}
  (let [mailer-client (:mailer (:deps this))]
    (mailer/send mailer-client (:email event) (str "Hi " (:user-name event) ", your grade in the quiz is: " (:grade event)))))

(defrecord quiz-communication [deps]
    event-handler/handler

  (handle [this event]
    (execute this event)))

(defmethod ig/init-key ::quiz-communication
  [_ {:keys [mailer]}]
  (->quiz-communication {:mailer mailer}))
