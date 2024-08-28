package com.vtw.dna;

import org.apache.camel.opentelemetry.starter.CamelOpenTelemetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@CamelOpenTelemetry
@SpringBootApplication
public class DnaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DnaApplication.class, args);
    }

}
