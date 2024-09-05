package aiep.inf;

import org.apache.camel.opentelemetry.starter.CamelOpenTelemetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@CamelOpenTelemetry
@SpringBootApplication
public class AiepInfApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiepInfApplication.class, args);
    }

}
