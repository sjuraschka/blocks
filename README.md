# Blocks

## Running

```
lein repl
```
Then in the REPL:
```
(start! 6543)
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



Changes to templates, blocks and pages are updated immediately.

Changes to the server require another `start!` and refresh.


## Export

`lein repl`
`(start! 6543)`
`lein cljsbuild once prod`
`phantomjs export.js http://localhost:6543/ www.braidchat.com / braid ./export/`


## Upload to S3

export (as above)

set the following in `profiles.clj`:

```
{:profile_name {:env {:s3-bucket "bucket_name"
                      :s3-id "S3_ID"
                      :s3-secret "S3_SECRET"
                      :s3-endpoint "s3-endpoint"}}}
```

then:

`lein with-profiles +profile_name repl`
`(blocks.server.upload/upload!)`
