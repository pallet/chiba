(ns chiba.plugin-test
  (:require
   [chiba.plugin :as plugin])
  (:use
   clojure.test))

(deftest plugins-test
  (let [namespaces (plugin/plugins #"chiba.test\..*" #"chiba.test.utils")]
    (is (= #{'chiba.test.a 'chiba.test.b} namespaces)))
  (let [namespaces (plugin/plugins #"chiba.test\..*")]
    (is (= #{'chiba.test.a 'chiba.test.b 'chiba.test.utils} namespaces))))
