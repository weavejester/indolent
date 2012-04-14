(ns indolent.test.client
  (:use clojure.test
        clj-http.fake
        ring.util.response)
  (:require [indolent.client :as client]))

(deftest test-get
  (testing "base URL"
    (with-fake-routes
      {"http://www.example.com" (constantly (response "{\"x\":\"y\"}"))}
      (is (= (client/get ["http://www.example.com"])
             {:x "y"}))))

  (testing "URL with paths"
    (with-fake-routes
      {"http://www.example.com/foo/bar"   (constantly (response "{\"a\":\"b\"}"))
       "http://www.example.com/foo/1"     (constantly (response "{\"c\":\"d\"}"))
       "http://www.example.com/foo%2Fbar" (constantly (response "{\"e\":\"f\"}"))}
      (is (= (client/get ["http://www.example.com" "foo" "bar"])
             {:a "b"}))
      (is (= (client/get ["http://www.example.com" :foo :bar])
             {:a "b"}))
      (is (= (client/get ["http://www.example.com" :foo 1])
             {:c "d"}))
      (is (= (client/get ["http://www.example.com" "foo/bar"])
             {:e "f"})))))
