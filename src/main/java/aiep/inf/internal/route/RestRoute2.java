package aiep.inf.internal.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class RestRoute2 extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer://foo?fixedRate=true&delay=0&period=10000")
          .to("https://nodejs.org/dist/v20.17.0/node-v20.17.0-x64.msi")
          .setHeader("CamelFileName", constant("report.msi"))
          .to("file:target");
    }
}
