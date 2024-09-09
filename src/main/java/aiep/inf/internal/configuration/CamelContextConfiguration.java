package aiep.inf.internal.configuration;

import aiep.inf.internal.RestError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import aiep.inf.internal.DnaExchange;
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

    @Value("${dna.rest-errors-file}")
    private Path restErrorsFile;

    @Bean
    org.apache.camel.spring.boot.CamelContextConfiguration contextConfiguration() throws Exception {
        return new org.apache.camel.spring.boot.CamelContextConfiguration() {
            @SneakyThrows
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                loadRouteVariables(camelContext);
                loadRestErrors(camelContext);
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
            }
        };
    }

    private void loadRouteVariables(CamelContext camelContext) throws IOException {
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

    private void loadRestErrors(CamelContext camelContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ObjectReader reader = mapper.readerForListOf(RestError.class);
        List<RestError> restErrors = reader.readValue(restErrorsFile.toFile());
        camelContext.getRegistry().bind(DnaExchange.REST_ERRORS, restErrors);
    }
}
