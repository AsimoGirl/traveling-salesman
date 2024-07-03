# Traveling salesman problem using simulated annealing with threshold acceptance

The comments and contents of the project are in Spanish as they were used for educational purposes.

You need to have gradle version 6 or higher

### Generate documentation
To generate documentation use

```bash
$ ./gradlew dokkaHtml
```
and within build/dokka the documentation will appear

### Execution
You will have to send the program a file with the list of cities, the beginning of the seeds and the end of them.
The program also works with a single seed.
If you want to test a range of seeds, a txt called results.txt will appear inside a results folder, which will have the evaluation of all the seeds and will give the best solution among them.


The program runs as follows within the current directory:

```bash
$ ./gradlew run -Pcities=FILE -PseedS=Int -PseedF=Int
```

Example of execution if you want to test a range of seeds:
```bash
$ ./gradlew run -Pcities=input/instance-40.txt -PseedS=20 -PseedF=25
```
Example of execution if you only want one seed:
```bash
$ ./gradlew run -Pcities=input/instance-150.txt -PseedS=20 -PseedF=20
```

The input format for cities should be like the examples in input

Within the result/resultsIterandoSemillas folder we have the costs with 1000 different seeds at the two example distances, as well as the best solution found among those 1000.
