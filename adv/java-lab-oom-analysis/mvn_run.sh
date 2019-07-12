#!/bin/bash

mvn clean package

BASE_DIR=$(dirname "$0")
JAR_EXE="${BASE_DIR}/target/java-lab-oom-analysis-1.0-SNAPSHOT.jar"
java -Xmx10m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=. -jar ${JAR_EXE}