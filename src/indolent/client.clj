(ns indolent.client
  (:refer-clojure :exclude [get])
  (:import java.net.URLEncoder)
  (:require [clj-http.client :as http]
            [clojure.string :as str])
  (:use [slingshot.slingshot :only [try+]]
        [clojure.core.incubator :only [-?>]]))

(defn- url-encode [s]
  (URLEncoder/encode s "UTF-8"))

(defn- make-url [[root & paths]]
  (->> (map url-encode paths)
       (str/join "/")
       (str root "/")))

(defn get [url]
  (try+
   (-?> (make-url url)
        (http/get {:as :json})
        :body)
   (catch [:status 404] _ nil)))
