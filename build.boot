(def +project+ 'miraj/polymer)
(def +version+ "1.0.0-SNAPSHOT")

(set-env!
 :resource-paths #{"src/main/clj"}

 ;; :checkouts '[[miraj/co-dom                  "1.0.0-SNAPSHOT"]
 ;;              ;; [miraj/html                    "5.1.0-SNAPSHOT"]
 ;;              [miraj/core                    "1.0.0-SNAPSHOT"]
 ;;              ]

 :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"}])

 :dependencies '[[org.clojure/clojure "1.9.0-alpha17"]
                 [miraj/co-dom               "1.0.0-SNAPSHOT"]

                 ;; testing
                 ;; [miraj/core                    "1.0.0-SNAPSHOT" :scope "test"]
                 ;; [miraj/html                    "5.1.0-SNAPSHOT" :scope "test"]
                 ;; [miraj.polymer/iron            "1.2.3-SNAPSHOT" :scope "test"]
                 ;; [miraj.polymer/paper           "1.2.3-SNAPSHOT" :scope "test"]
                 ;; [miraj/boot-miraj "0.1.0-SNAPSHOT"]
                 ;; [adzerk/boot-cljs "1.7.228-1" :scope "test"]
                 ;; [adzerk/boot-cljs-repl "0.3.0" :scope "test"]


                 [miraj/boot-miraj           "0.1.0-SNAPSHOT" :scope "test"]
                 ;; [pandeiro/boot-http "0.7.3"           :scope "test"]
                 ;; [samestep/boot-refresh "0.1.0" :scope "test"]
                 [adzerk/boot-test "1.0.7" :scope "test"]])

(require '[miraj.boot-miraj :as miraj]
         ;; '[samestep.boot-refresh :refer [refresh]]
         ;; '[pandeiro.boot-http :as http :refer :all]
         '[adzerk.boot-test :refer [test]])

(task-options!
 repl {:port 8080}
 pom {:project +project+
      :version +version+
      :description "Miraj Polymer functions and elements"
       :url       "https://github.com/miraj-project/polymer"
       :scm {:url "https://github.com/miraj-project/polymer.git"}
       :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}}
 jar {:manifest {"root" "miraj"}}
 push {:repo "clojars"})

(deftask build
  "build"
  []
  (comp (pom)
        (jar)))

(deftask install-local
  "build"
  []
  (comp (build)
        (target)
        (install)))

(deftask deploy
  "deploy to clojars"
  []
  (comp (install-local)
        (push)))

(deftask check
  "watch etc. for dev as a checkout"
  []
  (comp (watch)
        (notify :audible true)
        (build)
        (target)
        (install)))

;; (deftask systest
;;   "serve and repl for integration testing; run this, then eval test/system/clj code using cider"
;;   []
;;   (set-env! :resource-paths #(conj % "test/system/clj"))
;;   (comp
;;    (build)
;;    (serve :dir "target")
;;    (cider)
;;    (repl)
;;    (watch)
;;    (notify :audible true)
;;    (target)))

(deftask utest
  "run unit tests"
  [n namespaces  NS  #{sym}  "test ns"]
  (set-env! :source-paths #(conj % "test/unit/clj"))
  (test :namespaces namespaces))
