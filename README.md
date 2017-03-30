# JKnight
Various algorithms solving the [knight's tour](https://en.wikipedia.org/wiki/Knight%27s_tour) problem. Written in Java. Supports multithreaded solving.

### Prerequisites

+ [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
+ [Gradle](https://gradle.org/)
+ [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Project Files included) 

### Algorithms
+ Backtracking ``--method backtracking`` 
+ Backtracking with random tiebreak ``--method backtracking-random`` 
+ Warnsdorff's Rule ``--method warnsdorf`` 

### Build
Run ``` gradle build ``` inside root folder.

### Usage
```
JKnight [options]
  Options:
    --algorithm
       Solving algorithm: warnsdorf, backtracking, backtracking-random
       Default: warnsdorf
    --board
       Rows and columns of the board: e.g. --board 8 8
    --help
       Display Help
       Default: false
    --solutions
       Number of attempted solutions, -1 = unlimited
       Default: -1
    --start
       Start position of the knight: e.g. --start 4 4
    --threads
       Number of knights and threads
       Default: 1
```

### License

Copyright © 2017 Stefan Kerkmann. This software released under the MIT License.