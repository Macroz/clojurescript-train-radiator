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
- `src/handler.clj`: the code is here
- `resources/public/style.css`: the CSS style file

Useful documentation
====================

- [ClojureDocs](https://clojuredocs.org) – the Clojure standard library documentation, searchable
- [re-frame](https://github.com/Day8/re-frame/) – the  web framework used in this project that does what React and Redux do
- [Hiccup wiki](https://github.com/weavejester/hiccup/wiki) - how to generate HTML
- [cljs-time](https://github.com/andrewmcveigh/cljs-time) - how to handle time

Tasks
=====

- make the page prettier
- make the station configurable
- poll timetables
- show only time table rows for this station
- only show future events
- collect statistics of late trains

Interactive development
=======================

Interactive development means having a REPL (command prompt) where you
can interact with the code and try out things, while also running the
server.

Here's how:

1. Start an interactive clojure environment, for example:
   - run `lein repl` in a terminal
   - use `M-x cider-jack-in-clojurescript` in Emacs (after installing [cider](https://github.com/clojure-emacs/cider))
   - install a Clojure IDE like [Cursive for IntelliJ](https://cursive-ide.com/userguide/)
2. Try out things and edit code
3. Code is automatically refreshed thanks to Figwheel

Where to go from here?
======================

TODO
