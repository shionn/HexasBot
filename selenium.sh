#!/bin/bash
git pull
mvn clean install -Dmaven.war.skip=true
cd HexasSeleniumScanner
xvfb-run mvn exec:java
