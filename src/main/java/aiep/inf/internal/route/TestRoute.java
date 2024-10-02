package aiep.inf.internal.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class TestRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {
        rest("/download").get().produces(MediaType.APPLICATION_OCTET_STREAM_VALUE).routeId("downloadFile").to(direct("RestDownloadFile").getUri());

        from(direct("RestDownloadFile"))
          .pollEnrich("file:otel?fileName=opentelemetry-javaagent.jar&noop=true&idempotent=false")
          .setHeader("Content-Disposition", simple("attachment;filename=opentelemetry-javaagent.jar"));


        from("timer://foo?fixedRate=true&delay=0&period=5000")
          .to("direct://Download1");

    }
}
