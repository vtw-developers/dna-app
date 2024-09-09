package aiep.inf.api.error.processor;

import aiep.inf.api.error.FaultMessage;
import aiep.inf.api.key.exception.InvalidApiKeyException;
import aiep.inf.internal.DnaExchange;
import aiep.inf.internal.RestError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component("ApiErrorHandlerProcessor")
public class ApiErrorHandlerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String message = "";
        String errorCode = "";

        if (exception != null) {
            CamelContext context = exchange.getContext();
            List<RestError> restErrors = context.getRegistry().lookupByNameAndType(DnaExchange.REST_ERRORS, List.class);
            for (RestError restError : restErrors) {
                if (Class.forName(restError.getException()).isInstance(exception)) {
                    message = restError.getMessage();
                    errorCode = restError.getErrorCode();
                    break;
                }
            }
        }

        FaultMessage faultMessage = new FaultMessage();
        faultMessage.setErrorCode(errorCode);
        faultMessage.setMessage(message);

        exchange.getMessage().setBody(faultMessage);
    }
}
