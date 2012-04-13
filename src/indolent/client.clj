(ns indolent.client
  (:refer-clojure :exclude [get])
  (:require [clj-http.client :as http])
  (:use [slingshot.slingshot :only [try+]]))

(defn get [[root & path]]
  (try+
   (http/get root)
   (catch [:status 404] _ nil)))
