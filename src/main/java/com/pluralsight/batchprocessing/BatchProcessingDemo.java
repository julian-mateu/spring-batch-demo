package com.pluralsight.batchprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchProcessingDemo {

  public static void main(String[] args) {
    System.exit(
        SpringApplication.exit(
            SpringApplication.run(
                BatchProcessingDemo.class,
                args
            )
        )
    );
  }
}
