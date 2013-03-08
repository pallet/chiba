(ns chiba.plugin-test
  (:require
   [chiba.plugin :as plugin])
  (:use
   clojure.test
   [clojure.java.io :only [file]]))

(deftest plugins-test
  (let [namespaces (plugin/plugins "chiba.test." #"chiba.test.utils")]
    (is (= #{'chiba.test.a 'chiba.test.b} namespaces)))
  (let [namespaces (plugin/plugins "chiba.test.")]
    (is (= #{'chiba.test.a 'chiba.test.b 'chiba.test.utils} namespaces))))

(deftest data-plugins-test
  (let [files (plugin/data-plugins "chiba/test/" #"chiba/test/utils")]
    (is (= #{ "chiba/test/a.clj" "chiba/test/b.clj"} files)))
  (let [files (plugin/data-plugins "chiba/test/")]
    (is (= #{"chiba/test/a.clj" "chiba/test/b.clj" "chiba/test/utils.clj"}
           files))))
