#!/bin/bash
git pull
mvn clean install -Dmaven.war.skip=true
cd HexasBotJfxScanner
mvn exec:java
xvfb-run mvn javafx:run
