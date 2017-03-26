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
`/resources/data/assets`


Changes to templates, blocks and pages are updated immediately.

Changes to the server require another `start!` and refresh.


## Deploying a Page

### Prerequisites

#### Set your logins and passwords

Set the following in `profiles.clj`:

```
{:export
  {:env
    {:cdn77-login "..."
     :cdn77-password "..."
     :cdn77-storage-location "..."
     :s3-bucket "..."
     :s3-id "..."
     :s3-secret "..."
     :cloudflare-email "..."
     :cloudflare-key "..."}}}
```

#### Install `phantomjs`

Mac: `brew install phantomjs`
Unix: `apt-get install phantomjs`

### The process:

`lein cljsbuild once prod`

`lein with-profiles +bloom repl`

`(start-release! 6543)`

`(require 'blocks.deploy.core)`

`(blocks.deploy.core/deploy! "www.domain.com" "/")`

