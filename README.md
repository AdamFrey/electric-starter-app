# Electric + Shadow CSS Live Reload Problem

Steps to reproduce:

* `clj -A:dev -X user/main`
* load localhost:8080 in your browser
* connect your repl to port 9001
* Eval ``(css-release)`` in user.clj (in the comment block)
* Now modify the text `Example 1` in todo_list.cljc to `Example 2` and save the file
* Text should disappear with `display: none`
