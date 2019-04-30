# Turing Machine Assignment

This repository holds the sources to the assignment, solutions, and web server for CS 3102's turing machine assignment. The student source files can be constructed by running
```
$ make archive
```
which produces a zip file containing any Java source files and test files that the student might need. The assignment description can be constructed by running
```
$ make pdf
```
which produces a pdf from the Latex document. Note: This assumes you have a Tex distribution installed on your system. Please execute the Makefile before committing any changes to the assignment.

### Testing
Students can run automated tests on their parsers and simulators by executing *TestParser.java* and *TestSimulator.java*. When they are ready for submission, they should execute *Submit.java*, which produces a zip file containing all Java source files and turing machine programs in their working directory.

### Website
The website is powered by a Go server that interfaces with a Java simulator through JSON. It can be accessed publicly at [tmsim.akhil.cc](https://www.tmsim.akhil.cc). The simulator strives to be paranoid in that it should report helpful error messages to the student for debugging purposes.

### Deployment
Docker must be installed on your machine. By running
```
$ make deploy
```
a container image is built, tagged, and uploaded to the docker hub, from which the DigitalOcean server pulls down and runs the updated image. Deployment access is currently only given to Akhil Indurti, but changes to the application can be made by any committers to the repo.