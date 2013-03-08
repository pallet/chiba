(ns chiba.classpath-files
  "Find files on the classpath"
  (:use
   [bultitude.core :only [classpath-files] :as bultitude]
   [clojure.java.io :only [file]])
  (:import
   [java.io File]
   [java.util.jar JarFile JarEntry]))

(defn- files-in-jar [^File jar]
  (let [jarfile (JarFile. jar)]
    (for [^JarEntry entry (enumeration-seq (.entries jarfile))]
      (.getName entry))))

(defn file->files
  "Map a classpath file to the files it contains. `prefix` allows for reducing
the file search space. For large directories on the classpath, passing a
`prefix` can provide significant efficiency gains."
  [prefix ^File f]
  (cond
   (.isDirectory f)
   (let [^File path (if prefix (file f prefix) (file f))
         n (inc (count (.getPath f)))]
     (when (.exists path)

       (->>
        (file-seq path)
        (filter #(not (.isDirectory ^File %)))
        (map #(subs (.getPath ^File %) n)))))

   (#'bultitude/jar? f)
   (let [files (files-in-jar f)]
     (if prefix
       (filter #(and % (.startsWith ^String % prefix)) files)
       files))))

(def classpath->collection #'bultitude/classpath->collection)
(def classpath->files #'bultitude/classpath->files)

(defn files-on-classpath
  "Return classpath relative file paths matching the given prefix both on disk
and inside jar files.  If :prefix is passed, only return files that begin with
this prefix.  If :classpath is passed, it should be a seq of File objects or a
classpath string.  If it is not passed, default to java.class.path and the
current classloader, assuming it is a URL classloader."
  [& {:keys [prefix classpath] :or {classpath (classpath-files)}}]
  (mapcat
   (partial file->files prefix)
   (->> classpath
        classpath->collection
        classpath->files)))
