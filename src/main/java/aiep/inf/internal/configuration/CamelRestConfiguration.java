package aiep.inf.internal.configuration;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRestConfiguration extends RouteConfigurationBuilder {
    @Override
    public void configuration() {
        restConfiguration().inlineRoutes(false);
    }
}
