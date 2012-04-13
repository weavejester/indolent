(ns indolent.client
  (:refer-clojure :exclude [get])
  (:require [clj-http.client :as http]))

(defn get [[root & path]]
  (http/get root))
