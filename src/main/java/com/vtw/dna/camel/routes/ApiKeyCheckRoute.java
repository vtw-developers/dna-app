package com.vtw.dna.camel.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class ApiKeyCheckRoute extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(direct("ApiKeyCheck"))
        .log("API 키 검사")
        .delay(300).syncDelayed();
    }
}
