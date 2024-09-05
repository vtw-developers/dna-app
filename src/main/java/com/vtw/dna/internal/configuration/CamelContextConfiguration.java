package com.vtw.dna.internal.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vtw.dna.internal.DnaExchange;
import lombok.SneakyThrows;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
public class CamelContextConfiguration {

    @Value("${dna.template-parameters-directory}")
    private Path templateParametersDirectory;

    @Bean
    org.apache.camel.spring.boot.CamelContextConfiguration contextConfiguration() throws Exception {
        return new org.apache.camel.spring.boot.CamelContextConfiguration() {
            @SneakyThrows
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                buildRouteVariables(camelContext);
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
            }
        };
    }

    private void buildRouteVariables(CamelContext camelContext) throws IOException {
        Files.find(templateParametersDirectory,
                        Integer.MAX_VALUE,
                        (filePath, fileAttr) -> fileAttr.isRegularFile())
                .filter(path -> path.toString().endsWith(".templated.yaml"))
                .forEach(path -> {
                    try {
                        String yaml = Files.readString(path);
                        List<Map<String, Object>> list = new ObjectMapper(new YAMLFactory()).readValue(yaml, List.class);

                        for (Map<String, Object> map : list) {
                            String flowId = (String) map.get("flowId");
                            Map<String, Object> parameters = Collections.unmodifiableMap((Map<String, Object>) map.get("parameters"));
                            camelContext.setVariable("route:" + flowId + ":" + DnaExchange.TEMPLATE_PARAMETERS, parameters);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
