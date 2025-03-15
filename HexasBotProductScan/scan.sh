#!/bin/bash
git pull
mvn clean compile exec:java
xvfb-run mvn javafx:run
