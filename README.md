# chiba

Chiba is no sprawl, just a small library to help you to augment your code with plugins.

## Usage

Return all namespaces matching a regular expression:

```clojure
    (require 'chiba.plugin)
    (chiba.plugin/plugins #"my.plugin.root-namespace\..*")
```

Return all namespaces matching a regular expression, excluding those that match
an exclusion regex:

```clojure
    (require 'chiba.plugin)
    (chiba.plugin/plugins
      #"my.plugin.root-namespace\..*" #"my.plugin.root-namespace.utils.*")
```

## License

Copyright (C) 2011 Hugo Duncan

Distributed under the Eclipse Public License.
