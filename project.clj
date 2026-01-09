(def slf4j-version "2.0.17")
(def logback-version "1.5.24")
(def i18n-version "1.0.2")
(def kitchensink-version "3.5.4")

(defproject org.openvoxproject/trapperkeeper "4.3.1-SNAPSHOT"
  :description "A framework for configuring, composing, and running Clojure services."

  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}

  :min-lein-version "2.9.1"

  ;; Abort when version ranges or version conflicts are detected in
  ;; dependencies. Also supports :warn to simply emit warnings.
  ;; requires lein 2.2.0+.
  :pedantic? :abort

  ;; These are to enforce consistent versions across dependencies of dependencies,
  ;; and to avoid having to define versions in multiple places. If a component
  ;; defined under :dependencies ends up causing an error due to :pedantic? :abort,
  ;; because it is a dep of a dep with a different version, move it here.
  :managed-dependencies [[org.clojure/clojure "1.12.4"]
                         [commons-io "2.21.0"]
                         [org.openvoxproject/kitchensink ~kitchensink-version]
                         [org.openvoxproject/kitchensink ~kitchensink-version :classifier "test"]]
  
  :dependencies [[org.clojure/clojure]
                 [org.clojure/tools.logging "1.3.1"]
                 [org.clojure/tools.macro "0.2.2"]
                 [org.clojure/core.async "1.8.741"]

                 [org.slf4j/slf4j-api ~slf4j-version]
                 [org.slf4j/log4j-over-slf4j ~slf4j-version]
                 [ch.qos.logback/logback-classic ~logback-version]
                 ;; even though we don't strictly have a dependency on the following two
                 ;; logback artifacts, specifying the dependency version here ensures
                 ;; that downstream projects don't pick up different versions that would
                 ;; conflict with our version of logback-classic
                 [ch.qos.logback/logback-core ~logback-version]
                 [ch.qos.logback/logback-access ~logback-version]

                 [clj-time "0.15.2"]
                 [clj-commons/fs "1.6.312"]

                 [prismatic/plumbing "0.6.0"]
                 [prismatic/schema "1.4.1"]

                 [beckon "0.1.1"]

                 [org.openvoxproject/typesafe-config "1.0.0"]
                 ;; exclusion added due to dependency conflict over asm and jackson-dataformat-cbor
                 ;; see https://github.com/puppetlabs/trapperkeeper/pull/306#issuecomment-1467059264
                 [org.openvoxproject/kitchensink :exclusions [cheshire]]
                 [org.openvoxproject/i18n ~i18n-version]
                 [nrepl/nrepl "0.9.0"]
                 [io.github.clj-kondo/config-slingshot-slingshot "1.0.0"]

                 [com.kohlschutter.junixsocket/junixsocket-core "2.10.1" :extension "pom"]]

  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/CLOJARS_USERNAME
                                     :password :env/CLOJARS_PASSWORD
                                     :sign-releases false}]]

  ;; Convenience for manually testing application shutdown support - run `lein test-external-shutdown`
  :aliases {"test-external-shutdown" ["trampoline" "run" "-m" "examples.shutdown-app.test-external-shutdown"]}

  ;; By declaring a classifier here and a corresponding profile below we'll get an additional jar
  ;; during `lein jar` that has all the code in the test/ directory. Downstream projects can then
  ;; depend on this test jar using a :classifier in their :dependencies to reuse the test utility
  ;; code that we have.
  :classifiers [["test" :testutils]]

  :profiles {:dev {:source-paths ["examples/shutdown_app/src"
                                  "examples/java_service/src/clj"]
                   :java-source-paths ["examples/java_service/src/java"]
                   :dependencies [[org.openvoxproject/kitchensink :classifier "test" :exclusions [cheshire]]]}

             :testutils {:source-paths ^:replace ["test"]}
             :uberjar {:aot [puppetlabs.trapperkeeper.main]
                       :classifiers ^:replace []}}

  :plugins [[jonase/eastwood "1.4.3" :exclusions [org.clojure/clojure]]
            [org.openvoxproject/i18n ~i18n-version]]

  :eastwood {:ignored-faults {:reflection {puppetlabs.trapperkeeper.logging [{:line 92}]
                                           puppetlabs.trapperkeeper.internal [{:line 174}]
                                           puppetlabs.trapperkeeper.testutils.logging true
                                           puppetlabs.trapperkeeper.testutils.logging-test true
                                           puppetlabs.trapperkeeper.services.nrepl.nrepl-service-test true
                                           puppetlabs.trapperkeeper.plugins-test true}
                              :local-shadows-var {puppetlabs.trapperkeeper.config-test true
                                                  puppetlabs.trapperkeeper.services-test true
                                                  java-service-example.java-service true
                                                  puppetlabs.trapperkeeper.optional-deps-test true}
                              :deprecations {puppetlabs.trapperkeeper.testutils.logging true
                                             puppetlabs.trapperkeeper.testutils.logging-test true
                                             puppetlabs.trapperkeeper.logging-test true}
                              :def-in-def {puppetlabs.trapperkeeper.optional-deps-test true}}

             :continue-on-exception true}

  :main puppetlabs.trapperkeeper.main)
