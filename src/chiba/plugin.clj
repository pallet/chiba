(ns chiba.plugin
  "Plugin through namespace discovery"
  (:use
   [bultitude.core :only [namespaces-on-classpath]]
   [chiba.classpath-files :only [files-on-classpath]]))

(defn plugins
  "Find the available plugin namespaces. Returns a set of all namespaces with
   the given prefix that do not match exclusion-regex."
  ([ns-prefix exclusion-regex]
     (try
       (->> (namespaces-on-classpath :prefix ns-prefix)
            (remove #(re-find exclusion-regex (name %)))
            (set))
       (catch java.io.FileNotFoundException _)))
  ([ns-prefix]
     (try
       (->> (namespaces-on-classpath :prefix ns-prefix)
            (set))
       (catch java.io.FileNotFoundException _))))

(defn backend
  "Given a set of plugins, and a sequence of preferred plugins, returns
   the first preferred plugin, or the first plugin if no preferred plugins
   are found"
  [plugins preferrences]
  (or (first (filter plugins preferrences))
      (first plugins)))



(defn data-plugins
  "Find the available plugin resources. Returns a set of all paths with the
given prefix that do not match exclusion-regex.  The separator in both prefix
and exckusion regex is the file separator, /."
  ([path-prefix exclusion-regex]
     (try
       (->> (files-on-classpath :prefix path-prefix)
            (remove #(re-find exclusion-regex (name %)))
            (set))
       (catch java.io.FileNotFoundException _)))
  ([path-prefix]
     (try
       (->> (files-on-classpath :prefix path-prefix)
            (set))
       (catch java.io.FileNotFoundException _))))
