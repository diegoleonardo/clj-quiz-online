(ns application.handler.quiz-corrector
  (:require [application.handler.event-handler :as event-handler]
            [domain.repository.repository :as repo]
            [application.mediator.mediator :as mediator]
            [integrant.core :as ig]))

(defn- correct-answers [event questions]
  (let [limit (count questions)]
    (loop [index  0
           result 0]
      (if (= index limit)
        result
        (recur (inc index)
               (let [answer         (:answer (nth (:answers event) index))
                     correct-answer (:correct-answer (nth questions index))]
                 (if (= answer correct-answer)
                   (inc result)
                   result)))))))

(defrecord quiz-corrector [deps]
  event-handler/handler

  (handle [this event]
    (let [quiz            (repo/get-by-id (:repository (:deps this)) (:id-quiz event))
          questions       (:questions quiz)
          correct-answers (correct-answers event questions)
          grade           (-> (/ correct-answers (count questions))
                              (* 100))
          quiz-corrected  {:name      "quiz-corrected"
                           :user-name (:user-name event)
                           :email     (:email event)
                           :grade     grade}]
      (mediator/publish (:mediator event) quiz-corrected))))

(defmethod ig/init-key ::quiz-corrector
  [_ {:keys [repo]}]
  (->quiz-corrector {:repository repo}))
