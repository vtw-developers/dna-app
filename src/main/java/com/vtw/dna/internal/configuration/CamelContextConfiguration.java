package com.vtw.dna.internal.configuration;

import com.vtw.dna.internal.DataSourceInfo;
import com.vtw.dna.internal.RestError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vtw.dna.internal.DnaExchange;
import lombok.SneakyThrows;
import org.apache.camel.CamelContext;
import org.apache.camel.support.DefaultRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
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

    @Value("${dna.datasources-file}")
    private Path datasourcesFile;

    @Bean
    org.apache.camel.spring.boot.CamelContextConfiguration contextConfiguration() throws Exception {
        return new org.apache.camel.spring.boot.CamelContextConfiguration() {
            @SneakyThrows
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {
                loadRestErrors(camelContext);
                loadDataSources(camelContext);
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

    private void loadDataSources(CamelContext camelContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ObjectReader reader = mapper.readerForListOf(DataSourceInfo.class);
        List<DataSourceInfo> dataSourceInfos = reader.readValue(datasourcesFile.toFile());

        for (DataSourceInfo dataSourceInfo : dataSourceInfos) {
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            DataSource dataSource = dataSourceBuilder
                    .driverClassName(dataSourceInfo.getDriverClassName())
                    .url(dataSourceInfo.getUrl())
                    .username(dataSourceInfo.getUsername())
                    .password(dataSourceInfo.getPassword())
                    .build();
            DefaultRegistry defaultRegistry = camelContext.getRegistry(DefaultRegistry.class);
            defaultRegistry.bind(dataSourceInfo.getName(), dataSource);
        }
    }
}
