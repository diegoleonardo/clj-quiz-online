(ns domain.event.domain-event-test
  (:require [domain.event.domain-event :as domain]
            [clojure.test :refer [deftest testing is]]
            [clojure.spec.alpha :as s]))

(deftest quiz-submitted
  (testing "Should return true to a valid quiz-submitted schema"
    (is (true? (s/valid? domain/quiz-submitted-schema
                         {:name      "quiz-submitted"
                          :id-quiz   10
                          :user-name "Diego"
                          :email     "foo@gmail.com"
                          :answers   [{:id     1
                                       :answer "a"}]}))))
  (testing "Should return false to an invalid quiz-submitted schema"
    (is (false? (s/valid? domain/quiz-submitted-schema
                          {:name    "quiz-submitted"
                           :id-quiz 10
                           :email   "foo@gmail.com"
                           :answers [{:id     1
                                      :answer "a"}]})))))

(deftest quiz-corrected
  (testing "Should return true to a valid quiz-corrected schema"
    (is (true? (s/valid? domain/quiz-corrected-schema
                         {:name      "quiz-corrected"
                          :user-name "Foo"
                          :email     "foo@bar.com"
                          :grade     100}))))

  (testing "Should return false to an invalid quiz-corrected schema"
    (is (false? (s/valid? domain/quiz-corrected-schema
                          {:name      "quiz-corrected"
                           :email     "foo@bar.com"
                           :grade     100})))))
