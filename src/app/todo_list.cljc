(ns app.todo-list
  (:require #?(:clj [datascript.core :as d]) ; database on server
            [hyperfiddle.electric :as e]
            [shadow.css :refer [css]]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.electric-ui4 :as ui]))

(defonce !conn #?(:clj (d/create-conn {}) :cljs nil)) ; database on server
(e/def db) ; injected database ref; Electric defs are always dynamic

(e/defn Todo-list []
  (e/server
    (binding [db (e/watch !conn)]
      (e/client
        (dom/link (dom/props {:rel :stylesheet :href "/css/ui.css"}))
        (dom/p
          (dom/props {:class (css :bg-pink-100)})
          (dom/text "Example 1"))))))
