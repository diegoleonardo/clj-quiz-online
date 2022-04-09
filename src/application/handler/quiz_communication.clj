(ns application.handler.quiz-communication
  (:require [application.handler.event-handler :as event-handler]
            [application.service.mailer :as mailer]
            [integrant.core :as ig]))

(defrecord quiz-communication [deps]
    event-handler/handler

  (handle [this event]
    (let [mailer-client (:mailer (:deps this))]
      (println "mailer" mailer-client)
      (mailer/send mailer-client (:email event) (str "Hi " (:user-name event) ", your grade in the quiz is: " (:grade event))))))

(defmethod ig/init-key ::quiz-communication
  [_ {:keys [mailer]}]
  (->quiz-communication {:mailer mailer}))
