#!/bin/bash
git pull
mvn clean install
cd HexasBotFront
mvn tomcat7:redeploy 

