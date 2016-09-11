# JKnight
Various algorithms solving the [knight's tour](https://en.wikipedia.org/wiki/Knight%27s_tour) problem. Written in Java. Supports multithreaded solving.

### Algorithms
+ Backtracking ``--method backtracking`` 
+ Backtracking with random tiebreak ``--method backtracking-random`` 
+ Warnsdorff's Rule with random tiebreak ``--method warnsdorf`` 

### Usage
```
JKnight [options]
  Options:
  * --board
       Rows and columns of the board: e.g. --board 8 8
    --help
       Display Help
       Default: false
  * --method
       Solving algorithm: warnsdorf, backtrack, backtrack-random
    --random-start
       Generates random start positions for each knight
       Default: true
    --solutions
       Number of boards to be solved, -1 = unlimited
       Default: -1
    --start
       Start position of the knight: e.g. --start 4 4
    --threads
       Number of knights and threads
       Default: 1
```

### License

Copyright © 2016 Stefan Kerkmann. This software released under the MIT License.