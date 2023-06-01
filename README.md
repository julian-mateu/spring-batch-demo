# Spring Batch Demo

This is the demo project I used in the Pluralsight Audition
titled "Introduction to Batch Processing in Spring Batch".
It demonstrates how
[Spring Batch](https://spring.io/projects/spring-batch) can be used
to create a simple Batch Processing application for an ETL job.

The project contains a simple batch application that extracts
a [sample csv file](./src/main/resources/sample-data.csv),
makes a very simple transformation, and loads the result to 
an [in-memory database](./src/main/resources/schema-all.sql).

## Getting started

To run the application simply use gradle
```bash
./gradlew bootRun
```