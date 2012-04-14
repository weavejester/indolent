(ns indolent.test.client
  (:use clojure.test
        clj-http.fake
        ring.util.json-response)
  (:require [indolent.client :as client]))

(defn json-handler [x]
  (constantly (json-response x)))

(deftest test-get
  (testing "base URL"
    (with-fake-routes
      {"http://www.example.com" (json-handler {:x "y"})}
      (is (= (client/get ["http://www.example.com"])
             {:x "y"}))))

  (testing "URL with paths"
    (with-fake-routes
      {"http://www.example.com/foo/bar"   (json-handler {:a "b"})
       "http://www.example.com/foo/1"     (json-handler {:c "d"})
       "http://www.example.com/foo%2Fbar" (json-handler {:e "f"})}
      (is (= (client/get ["http://www.example.com" "foo" "bar"])
             {:a "b"}))
      (is (= (client/get ["http://www.example.com" :foo :bar])
             {:a "b"}))
      (is (= (client/get ["http://www.example.com" :foo 1])
             {:c "d"}))
      (is (= (client/get ["http://www.example.com" "foo/bar"])
             {:e "f"}))))

  (testing "accept header"
    (with-fake-routes
      {"http://example.com" (fn [req] (json-response (:headers req)))}
      (is (= (:Accept (client/get ["http://example.com"]))
             "application/json")))))
