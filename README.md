ClojureScript Train Radiator
============================

A small example project in ClojureScript. Fetches train information from
[rata.digitraffic.fi](https://rata.digitraffic.fi/) API and displays
it.

Getting started
===============

1. Install the Clojure build tool [Leiningen](https://leiningen.org).
2. Run `lein figwheel dev` to start the server and a browser
3. Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).
4. Change the code (browser refreshes automatically thanks to Figwheel)

Project structure
=================

- `project.clj`: the configuration file for Leiningen
- `src/cljs/clojurescript_train_radiator/`: the code is here
  - `events.cljs`
  - `subs.cljs`
  - `views.cljs`
- `resources/public/style.css`: the CSS style file

Useful documentation
====================

- [ClojureDocs](https://clojuredocs.org) – the Clojure standard library documentation, searchable
- [re-frame](https://github.com/Day8/re-frame/) – the  web framework used in this project that does what React and Redux do
- [Hiccup wiki](https://github.com/weavejester/hiccup/wiki) – how to generate HTML
- [cljs-time](https://github.com/andrewmcveigh/cljs-time) – how to handle time

Tasks
=====

- add a loading spinner
- make the page prettier (`resources/public/css/style.css`)
- make the station configurable (dropdown to app-db or path-parameter)
- show only time table rows for this station
- poll timetables (js/setInterval, dispatch event)
- only show future events (cljs-time, `in-the-future?`)
- fetch trains in own API backend (compojure, reitit etc.)
- use Garden for styling instead of plain CSS
- try out re-frame-10x or re-frisk
- localize the project (use tempura)
- browser tests (etaoin)
- collect statistics of late trains

Interactive development
=======================

Interactive development means having a REPL (command prompt) where you
can interact with the code and try out things, while also running the
server.

Here's how:

1. Start an interactive clojure environment, for example:
   - run `lein repl` in a terminal for plain Clojure or `lein figwheel dev` to run Figwheel `dev` profile
   - use `M-x cider-jack-in-clojurescript` in Emacs (after installing [cider](https://github.com/clojure-emacs/cider))
   - install a Clojure IDE like [Cursive for IntelliJ](https://cursive-ide.com/userguide/)
2. Try out things and edit code
3. Code is automatically refreshed thanks to Figwheel
