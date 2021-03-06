(def project 'avulias-botti)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :dependencies   '[[org.clojure/clojure "1.8.0" :scope "provided"]
                            [aleph "0.4.2-alpha8"]
                            [compojure "1.5.0"]
                            [environ "1.1.0"]
                            [javax.servlet/servlet-api "2.5"]
                            [morse "0.2.0"]
                            [ring/ring-json "0.4.0"]])

(task-options!
 aot {:namespace   '#{avulias-botti.server}}
 pom {:project     project
      :version     version
      :description "Avulias Telegram-botti"
      :url         "https://github.com/miikka/avulias-botti"
      :scm         {:url "https://github.com/miikka/avulias-botti"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 jar {:main        'avulias-botti.server
      :file        "avulias-botti-standalone.jar"})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot)
          (pom)
          (uber)
          (jar)
          (sift :include #{#"avulias-botti-standalone.jar"})
          (target :dir dir))))

(require '[avulias-botti.tasks :as tasks])
(deftask setup-webhook [] (tasks/setup-webhook))
