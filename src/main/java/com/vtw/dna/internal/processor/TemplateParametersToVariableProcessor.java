package com.vtw.dna.internal.processor;

import com.vtw.dna.internal.DnaExchange;
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

        if (parameters == null) {
            String routeId = exchange.getVariable("routeId", String.class);
            parameters = exchange.getVariable("route:" + routeId + ":" + DnaExchange.TEMPLATE_PARAMETERS, Map.class);
        }

        parameters.forEach((key, value) -> {
            exchange.setVariable(key, value);
        });
    }
}
