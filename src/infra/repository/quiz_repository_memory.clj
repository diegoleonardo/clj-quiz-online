(ns infra.repository.quiz-repository-memory
  #_(:refer-clojure :exclude [get])
  (:require [domain.repository.repository :as repo]
            [integrant.core :as ig]))

(def quizes [{:id-quiz   1
              :questions [{:id             1
                           :description    "Is javascript cool?"
                           :answers        [{:id "a" :description "Yes"}
                                            {:id "b" :description "No"}]
                           :correct-answer "a"}
                          {:id             2
                           :description    "Is ClojureScript better than JavaScript?"
                           :answers        [{:id "a" :description "Yes"}
                                            {:id "b" :description "No"}]
                           :correct-answer "a"}]}])

(defrecord quiz-repository-memory [state]
  repo/repository

  (get-by-id [this id]
    (let [quiz (first (filter #(= (:id-quiz %) id) (:state this)))]
      (if (nil? quiz)
        (throw (ex-info "Quiz not found" {:causes "Quiz is not inserted on the database"}))
        quiz))))

(defmethod ig/init-key ::repo
  [_ _]
  (->quiz-repository-memory quizes))
