(defproject org.openvoxproject/trapperkeeper "4.2.0-SNAPSHOT"
  :description "A framework for configuring, composing, and running Clojure services."

  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}

  :min-lein-version "2.9.0"

  :parent-project {:coords [org.openvoxproject/clj-parent "7.6.1"]
                   :inherit [:managed-dependencies]}

  ;; Abort when version ranges or version conflicts are detected in
  ;; dependencies. Also supports :warn to simply emit warnings.
  ;; requires lein 2.2.0+.
  :pedantic? :abort
  :dependencies [[org.clojure/clojure]
                 [org.clojure/tools.logging]
                 [org.clojure/tools.macro]
                 [org.clojure/core.async]

                 [org.slf4j/slf4j-api]
                 [org.slf4j/log4j-over-slf4j]
                 [ch.qos.logback/logback-classic]
                 ;; even though we don't strictly have a dependency on the following two
                 ;; logback artifacts, specifying the dependency version here ensures
                 ;; that downstream projects don't pick up different versions that would
                 ;; conflict with our version of logback-classic
                 [ch.qos.logback/logback-core]
                 [ch.qos.logback/logback-access]
                 ;; Janino can be used for some advanced logback configurations
                 [org.codehaus.janino/janino]

                 [clj-time]
                 [clj-commons/fs]

                 [prismatic/plumbing]
                 [prismatic/schema]

                 [beckon]

                 [org.openvoxproject/typesafe-config]
                 ;; exclusion added due to dependency conflict over asm and jackson-dataformat-cbor
                 ;; see https://github.com/puppetlabs/trapperkeeper/pull/306#issuecomment-1467059264
                 [org.openvoxproject/kitchensink "3.5.1" :exclusions [cheshire]]
                 [org.openvoxproject/i18n]
                 [nrepl/nrepl]
                 [io.github.clj-kondo/config-slingshot-slingshot "1.0.0"]]

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
                   :dependencies [[org.openvoxproject/kitchensink "3.5.1" :classifier "test" :exclusions [cheshire]]]}

             :testutils {:source-paths ^:replace ["test"]}
             :uberjar {:aot [puppetlabs.trapperkeeper.main]
                       :classifiers ^:replace []}}

  :plugins [[lein-parent "0.3.9"]
            [jonase/eastwood "1.4.3" :exclusions [org.clojure/clojure]]
            [org.openvoxproject/i18n "1.0.0"]]

  :eastwood {:ignored-faults {:reflection {puppetlabs.trapperkeeper.logging [{:line 92}]
                                           puppetlabs.trapperkeeper.internal [{:line 128}]
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
