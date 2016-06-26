# Blocks

## Running

```
lein repl
```
Then in the REPL:
```
(start-server! 6543)
```

In another terminal:
```
lein figwheel
```

Then visit:
```
http://localhost:6543/
```

## Editing

Templates are in:
`/src/braid/client/templates/`

Blocks are in:
`/resources/data/blocks.edn`

Pages are in:
`/resources/data/pages.edn`

Images are in:
`/resources/public/images`



Changes to templates are updated immediately.

Changes to blocks or pages require a refresh.

Changes to the server require another `start-server!` and refresh.


## Export

`lein repl`
`(start-server! 6543)`

`lein cljsbuild once prod`
`phantomjs export.js http://localhost:6543/ www.braidchat.com / ./export/`
