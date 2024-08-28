package com.vtw.dna.camel.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vtw.dna.edu.ApiKeyCheckProcessor;
import com.vtw.dna.edu.LcmsCallProcessor;
import org.apache.camel.Route;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.spring.boot.SpringBootCamelContext;
import org.apache.camel.support.RouteVariableRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class TestRoute extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
//        getContext().setVariable();
        //      from("direct:test").route
//        restConfiguration().inlineRoutes(false);

//        Map<String, Object> templateParameters = new HashMap<>();
//
//
//        getContext().setVariable("route:SampleTimer:DnaTemplateParameters", templateParameters);


        try (Stream<Path> paths = Files.list(Paths.get("C:\\Develop\\Projects\\dna-app\\template"))){
            paths.forEach(path -> {
                try {
                    String yaml = Files.readString(path);
                    List<Map<String, Object>> list = new ObjectMapper(new YAMLFactory()).readValue(yaml, List.class);
                    System.out.println(list);

                    for (Map<String, Object> map : list) {
                        String flowId = (String) map.get("flowId");
                        Map<String, Object> parameters = Collections.unmodifiableMap((Map<String, Object>) map.get("parameters"));
                        getContext().setVariable("route:" + flowId + ":DnaTemplateParameters", parameters);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }


//        from(direct("OpenApiTemplate"))
//        .to(direct("ApiKeyCheck"))
//        .process(exchange -> {
//            Map<String, Object> parameters = exchange.getVariable("route:" + exchange.getFromRouteId() + ":DnaTemplateParameters", Map.class);
//            exchange.setVariable("DnaTemplateParameters", parameters);
//        })
//        .process(new LcmsCallProcessor())
//        .setBody(constant(Map.of("name","우태진")));
//
//
//        from(direct("ApiKeyCheck"))
//        .process(new ApiKeyCheckProcessor())
//        .delay(300).syncDelayed();


//        from(timer("api1").period(3000)).id("api1")
//        .process(exchange -> {
//            Map<String, Object> parameters = exchange.getVariable("route:" + exchange.getFromRouteId() + ":DnaTemplateParameters", Map.class);
//            exchange.setVariable("DnaTemplateParameters", parameters);
//        })
//        .log("${variable.DnaTemplateParameters}");
//        from(timer("test").period(3000))
//                .log("hello");
    }
}
