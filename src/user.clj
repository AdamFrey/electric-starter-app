(ns user
  (:require [shadow.css.build :as cb]
            [clojure.java.io :as io]))

; lazy load dev stuff - for faster REPL startup and cleaner dev classpath
(def start-electric-server! (delay @(requiring-resolve 'hyperfiddle.electric-jetty-server/start-server!)))
(def shadow-start! (delay @(requiring-resolve 'shadow.cljs.devtools.server/start!)))
(def shadow-watch (delay @(requiring-resolve 'shadow.cljs.devtools.api/watch)))

(def electric-server-config
  {:host "0.0.0.0", :port 8080, :resources-path "public"})

(defn css-release [& args]
  (println "Writing to ui.css")
  (let [build-state
        (-> (cb/start)
            (assoc :preflight-src "")
            (cb/index-path (io/file "src" "app") {})
            (cb/generate
              '{:ui
                {:include
                 [app.todo-list*]}})
            (cb/write-outputs-to (io/file "resources" "public" "css")))]

    (doseq [mod (:outputs build-state)
            {:keys [warning-type] :as warning} (:warnings mod)]

      (prn [:CSS (name warning-type) (dissoc warning :warning-type)]))))

(comment
  (css-release)
  )

(defn main [& args]
  (css-release)
  (println "Starting Electric compiler and server...")
  (@shadow-start!)                      ; serves index.html as well
  (@shadow-watch :dev)                  ; depends on shadow server
                                        ; Shadow loads app.todo-list here, such that it shares memory with server.
  (def server (@start-electric-server! electric-server-config))
  (comment (.stop server)))

; Server-side Electric userland code is lazy loaded by the shadow build.
; WARNING: make sure your REPL and shadow-cljs are sharing the same JVM!

(comment
  (main) ; Electric Clojure(JVM) REPL entrypoint
  (hyperfiddle.rcf/enable!) ; turn on RCF after all transitive deps have loaded
  (shadow.cljs.devtools.api/repl :dev) ; shadow server hosts the cljs repl
  ; connect a second REPL instance to it
  ; (DO NOT REUSE JVM REPL it will fail weirdly)
  (type 1))
