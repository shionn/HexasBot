#!/bin/bash
git pull
mvn clean install
cd HexasBotJfxScanner
mvn exec:java
xvfb-run mvn javafx:run
