#!/bin/bash
git pull
xvfb-run mvn clean javafx:run
