#!/bin/bash
git pull
mvn clean install -Dmaven.war.skip=true
cd HexasBotSeleniumScanner
xvfb-run mvn exec:java -PProducts
#mvn exec:java
