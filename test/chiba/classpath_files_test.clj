(ns chiba.classpath-files-test
  (:require
   [chiba.classpath-files :refer [files-on-classpath]]
   [clojure.test :refer [deftest is]]))

(deftest files-on-classpath-test
  (is (files-on-classpath))
  (is (first (filter #(re-find #"bultitude" %) (files-on-classpath))))
  (is (first
       (filter #(re-find #"chiba" %) (files-on-classpath :prefix "chiba"))))
  (is (nil? (seq
             (filter
              #(re-find #"not-existing" %)
              (files-on-classpath :prefix "chiba")))))
  (is (nil? (seq (files-on-classpath :prefix "non-existing")))))
