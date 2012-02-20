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

## Install

### project.clj

Add the folowing to your `project.clj`:

```clojure
:dependencies [[org.cloudhoist/chiba "0.1.1"]]
:repositories {"sonatype" "http://oss.sonatype.org/content/repositories/releases"}
```
### pom.xml

Add the following to your `pom.xml`.

```xml
<dependencies>
  <dependency>
    <groupId>org.cloudhoist</groupId>
    <artifactId>chiba</artifactId>
    <version>0.1.1</version>
  </dependency>
<dependencies>

<repositories>
  <repository>
    <id>sonatype</id>
    <url>http://oss.sonatype.org/content/repositories/releases</url>
  </repository>
</repositories>
```

## License

Copyright (C) 2011, 2012 Hugo Duncan

Distributed under the Eclipse Public License.
