# chiba

Chiba is no sprawl, just a small library to help you to augment your code with
plugins.

## Usage

Return all namespaces matching a prefix:

```clojure
    (require 'chiba.plugin)
    (chiba.plugin/plugins "my.plugin.root-namespace")
```

Return all namespaces matching a prefix, excluding those that match an exclusion
regex:

```clojure
    (require 'chiba.plugin)
    (chiba.plugin/plugins
      "my.plugin.root-namespace" #"my.plugin.root-namespace.utils.*")
```

```clojure
    (require 'chiba.plugin)
    (chiba.plugin/data-plugins
       #"my.plugin.root-namespace.utils.*")
```

## Install

### project.clj

Add the folowing to your `project.clj`:

```clojure
:dependencies [[com.palletops/chiba "0.2.0"]]
```

## License

Copyright (C) 2011, 2012, 2013 Hugo Duncan

Distributed under the Eclipse Public License.
