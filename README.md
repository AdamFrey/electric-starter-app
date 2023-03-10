# Electric + Shadow CSS Live Reload Problem

Steps to reproduce:

* `clj -A:dev -X user/main`
* connect your repl
* Eval (css-release) in user.clj
* now start making edits to css classes and node text in todo_list.cljc
* you can re-eval css-release whenever to sync your CSS changes to the DOM
* I've found that at some point all the elements will disappear with display: none CSS attached to them
