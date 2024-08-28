package com.vtw.dna.camel.routes;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRestConfiguration extends RouteConfigurationBuilder {
    @Override
    public void configuration() {
        restConfiguration().inlineRoutes(false);
    }
}
