(ns quiz.dependencies-config
  (:require [integrant.core :as ig]
            [infra.service.mailer-memory :as mailer]
            [infra.mediator.handler-mediator :as mediator]
            [application.handler.quiz-communication :as quiz-communication]
            [application.handler.quiz-corrector :as quiz-corrector]
            [application.usecase.submit-quiz :as submit-quiz]
            [infra.repository.quiz-repository-memory :as repo]))

(defn config []
  {::mailer/mailer {:state []}

   ::repo/repo {}

   ::quiz-corrector/quiz-corrector {:repo     (ig/ref ::repo/repo)}

   ::quiz-communication/quiz-communication {:mailer (ig/ref ::mailer/mailer)}

   ::mediator/mediator {:handlers [{:event-name "quiz-corrector"
                                    :handler    (ig/ref ::quiz-communication/quiz-communication)}
                                   {:event-name "quiz-communication"
                                    :handler    (ig/ref ::quiz-communication/quiz-communication)}]}

   ::submit-quiz/submit-quiz {:mediator (ig/ref ::mediator/mediator)}})
