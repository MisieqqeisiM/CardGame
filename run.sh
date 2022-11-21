#!/bin/bash
mvn exec:java -D "exec.mainClass=com.example.cardgame.core.Server" &
mvn exec:java -D "exec.mainClass=com.example.cardgame.HelloApplication"
pkill java
