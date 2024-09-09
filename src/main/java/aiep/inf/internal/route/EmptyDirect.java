package aiep.inf.internal.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component(EmptyDirect.ROUTE_ID)
public class EmptyDirect extends EndpointRouteBuilder {

    public static final String ROUTE_ID = "EmptyDirect";

    @Override
    public void configure() throws Exception {
        from(direct(ROUTE_ID)).routeId(ROUTE_ID)
        .log(LoggingLevel.INFO, "EmptyDirect"); // 나중에 LoggingLevel Trace로 변경
    }
}
