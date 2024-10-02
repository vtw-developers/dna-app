package com.vtw.dna.api.biz.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component(Api2BizDirect.ROUTE_ID)
public class Api2BizDirect extends EndpointRouteBuilder {

    public static final String ROUTE_ID = "Api2BizDirect";

    @Override
    public void configure() throws Exception {
        from(direct(ROUTE_ID)).routeId(ROUTE_ID)
        .log("API2 업무 로직 실행");
    }
}
