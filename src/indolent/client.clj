(ns indolent.client
  (:refer-clojure :exclude [get])
  (:import java.net.URLEncoder
           clojure.lang.IDeref)
  (:require [clj-http.client :as http]
            [clojure.string :as str])
  (:use [slingshot.slingshot :only [try+]]
        [clojure.core.incubator :only [-?>]]))

(defrecord Resource [url]
  IDeref
  (deref [_]
    (try+
     (:body (http/get url {:as :json, :accept :json}))
     (catch [:status 404] _ nil))))

(defn- as-str [x]
  (if (keyword? x)
    (name x)
    (str x)))

(defn- url-encode [s]
  (URLEncoder/encode (as-str s) "UTF-8"))

(defn- make-url [[root & paths]]
  (->> (map url-encode paths)
       (str/join "/")
       (str root "/")))

(defn resource [& url-parts]
  (Resource. (make-url url-parts)))
