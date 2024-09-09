package aiep.inf.internal.processor;

import aiep.inf.internal.DnaExchange;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("TemplateParametersToVariableProcessor")
public class TemplateParametersToVariableProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> parameters = exchange.getVariable("route:" + exchange.getFromRouteId() + ":" + DnaExchange.TEMPLATE_PARAMETERS, Map.class);
        exchange.setVariable(DnaExchange.TEMPLATE_PARAMETERS, parameters);

        parameters.forEach((key, value) -> {
            exchange.setVariable(key, value);
        });
    }
}
