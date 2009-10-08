#!/bin/bash

echo $JAVA_HOME

echo $PATH

java -cp dao/target/test-classes;dao/target/dao.jar it.util.DayDataGenForReportTesting dao/src/test/resources/ReportTest.xml

