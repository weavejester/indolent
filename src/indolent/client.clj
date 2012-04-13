(ns indolent.client
  (:refer-clojure :exclude [get])
  (:require [clj-http.client :as http])
  (:use [slingshot.slingshot :only [try+]]
        [clojure.core.incubator :only [-?>]]))

(defn get [[root & path]]
  (try+
   (-?> (http/get root {:as :json})
        :body)
   (catch [:status 404] _ nil)))
