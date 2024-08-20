package com.vtw.dna.routes;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class TestRoute extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(timer("test").period(3000))
                .log("hello");
    }
}
