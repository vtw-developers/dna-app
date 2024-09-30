package aiep.inf.internal.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TestRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://foo?fixedRate=true&delay=0&period=1000")
          .to("direct://Download1");
    }
}
