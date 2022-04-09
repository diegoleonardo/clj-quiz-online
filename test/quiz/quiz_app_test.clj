(ns quiz.quiz-app-test
  (:require [clojure.test :refer :all]
            [quiz.quiz-app :refer :all]
            [infra.mediator.handler-mediator :as mediator]
            [infra.repository.quiz-repository-memory :as repo]
            [application.handler.quiz-corrector :as quiz-corrector]
            [application.handler.quiz-communication :as quiz-communication]
            [infra.service.mailer-memory :as mailer]
            [application.usecase.submit-quiz :as submit-quiz]
            [application.mediator.mediator :as mdt]))

(deftest quiz-app
  (testing "A user should submit a answered quiz and the grade should be calculated and a notification by email should be sent"
    (let [repository                 (repo/->quiz-repository-memory repo/quizes)
          mailer                     (mailer/->mailer-memory (atom []))
          mediator                   (mediator/->handler-mediator (atom []))
          quiz-corrector-handler     (quiz-corrector/->quiz-corrector {:repository   repository
                                                                       #_#_:mediator mediator})
          quiz-communication-handler (quiz-communication/->quiz-communication {:mailer mailer})
          submit-quiz                (fn [input]
                                       (mdt/register mediator {:event-name "quiz-submitted"
                                                               :handler    quiz-corrector-handler})
                                       (mdt/register mediator {:event-name "quiz-corrected"
                                                               :handler    quiz-communication-handler})
                                       (submit-quiz/execute mediator input))
          input                      {:name      "quiz-submitted"
                                      :user-name "John Doe"
                                      :email     "john.doe@gmail.com"
                                      :id-quiz   1
                                      :answers   [{:id 1 :answer "a"}
                                                  {:id 2 :answer "a"}]}]
      (submit-quiz input)
      (is (= 1 (count @(.state mailer)))))))
