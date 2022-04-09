(ns domain.event.domain-event
  (:require [clojure.spec.alpha :as s]))

(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(s/def :acct/email-type (s/and string? #(re-matches email-regex %)))

(s/def :acct/name string?)
(s/def :acct/user-name string?)
(s/def :acct/email :acct/email-type)
(s/def :acct/grade number?)
(s/def :acct/mediator fn?)

(s/def :unq/quiz-corrected
  (s/keys :req-un [:acct/name
                   :acct/user-name
                   :acct/email
                   :acct/grade]
          :opt-un [:acct/mediator]))

(def quiz-corrected-schema :unq/quiz-corrected)

(s/def :acct/id-quiz int?)
(s/def :acct/id int?)
(s/def :acct/answer string?)
(s/def :acct/answers
  (s/coll-of
   (s/keys :req-un [:acct/id :acct/answer])))

(s/def :unq/quiz-submitted
  (s/keys :req-un [:acct/name :acct/id-quiz :acct/user-name :acct/email :acct/answers]))

(def quiz-submitted-schema :unq/quiz-submitted)

#_(s/conform :unq/quiz-submitted
             {:name "quiz-submitted"
              :id-quiz 10
              :user-name "Diego"
              :email "foo@gmail.com"
              :answers [{:id 1
                         :answer "a"}]})

#_(s/valid? :unq/quiz-corrected
            {:name "quiz-corrected"
             :user-name "Foo"
             :email "foo@bar.com"
             :grade 100})
