(ns application.usecase.submit-quiz
  (:require [application.mediator.mediator :as mediator]
            [integrant.core :as ig]))

(defn execute [mediator-impl input]
  (->> (assoc input :mediator mediator-impl)
       (mediator/publish mediator-impl)))

(defmethod ig/init-key ::submit-quiz
  [_ {:keys [mediator]}]
  (fn [input]
    (execute mediator input)))
