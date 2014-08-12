(ns chiba.classpath-files
  "Find files on the classpath"
  (:use
   [bultitude.core :only [classpath-files] :as bultitude]
   [clojure.java.io :as io :only [file]])
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
(def clj? #'bultitude/clj?)
(def jar? #'bultitude/jar?)

(defn- clj? [^File f]
  (and (not (.isDirectory f))
       (or (.endsWith (.getName f) ".clj")
           (.endsWith (.getName f) ".class"))))

(defn files-in-dir
  "Return a seq of all files found in dir."
  [^File dir]
  (for [f (file-seq dir)
        :when (clj? f)]
    f))

(defn- files-in-jar
  "Return a sequence of clj files from a jar"
  [^File jar]
  (let [jarfile (JarFile. jar)]
    (for [entry (enumeration-seq (.entries jarfile))
          :let [f (io/file (.getName entry))]
          :when (clj? f)]
      f)))

(defn file->files
  "Map a classpath file to the namespaces it contains. `prefix` allows for
  reducing the namespace search space. For large directories on the classpath,
  passing a `prefix` can provide significant efficiency gains."
  [prefix ^File f]
  (cond
   (.isDirectory f) (->> (files-in-dir
                          (if prefix
                            (io/file f (-> prefix
                                           (.replaceAll "\\." "/")
                                           (.replaceAll "-" "_")))
                            f))
                         (map
                          #(io/file
                            (subs (.getPath %) (inc (count (.getPath f)))))))
   (jar? f) (let [file-list (files-in-jar f)]
              (if prefix
                (filter #(and % (.startsWith (.getPath %) prefix)) file-list)
                file-list))))

(defn files-on-classpath
  "Return classpath relative file paths matching the given prefix both on disk
and inside jar files.  If :prefix is passed, only return files that begin with
this prefix.  If :classpath is passed, it should be a seq of File objects or a
classpath string.  If it is not passed, default to java.class.path and the
current classloader, assuming it is a URL classloader."
  [& {:keys [prefix classpath] :or {classpath (classpath-files)}}]
  (->> classpath
       classpath->collection
       classpath->files
       (mapcat #(file->files prefix (io/file %)))
       (map #(.getPath %))))
