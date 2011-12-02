(ns chiba.plugin
  "Plugin through namespace discovery"
  (:require
   [clojure.java.classpath :as classpath]
   [clojure.tools.namespace :as namespace]))

;; ;; hack until a fix for  http://dev.clojure.org/jira/browse/CONTRIB-112
;; ;; is released

;; (defn loader-classpath
;;   "Returns a sequence of File paths from a classloader."
;;   [loader]
;;   (when (instance? java.net.URLClassLoader loader)
;;     (map
;;      #(java.io.File. (.getPath ^java.net.URL %))
;;      (.getURLs ^java.net.URLClassLoader loader))))

;; (defn classpath
;;   "Returns a sequence of File objects of the elements on the classpath."
;;   []
;;   (distinct
;;    (mapcat
;;     loader-classpath
;;     (take-while
;;      identity
;;      (iterate #(.getParent ^ClassLoader %) (clojure.lang.RT/baseLoader))))))

(defn plugins
  "Find the available plugin namespaces."
  ([matching-regex exclusion-regex]
     (try
       ;;binding [classpath/classpath classpath]
       (->> (namespace/find-namespaces-on-classpath)
            (filter #(re-find matching-regex (name %)))
            (remove #(re-find exclusion-regex (name %)))
            (set))
       (catch java.io.FileNotFoundException _)))
  ([matching-regex]
     (try
       ;;binding [classpath/classpath classpath]
       (->> (namespace/find-namespaces-on-classpath)
            (filter #(re-find matching-regex (name %)))
            (set))
       (catch java.io.FileNotFoundException _))))

(defn backend
  "Given a set of plugins, and a sequence of preferred plugins, returns
   the first preferred plugin, or the first plugin if no preferred plugins
   are found"
  [plugins preferrences]
  (or (first (filter plugins preferrences))
      (first plugins)))
