(ns quiz.quiz-app
  (:require [quiz.dependencies-config :as config]
            [integrant.core :as ig])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (ig/init (config/config)))
