package aiep.inf.api.error.processor;

import aiep.inf.api.error.FaultMessage;
import aiep.inf.api.key.InvalidApiKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component("ApiErrorHandlerProcessor")
public class ApiErrorHandlerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String message = "";
        String errorCode = "";

        if (exception instanceof InvalidApiKeyException) {
            message = "유효하지 않은 API 키입니다.";
            errorCode = "API-001";
        } else {
            log.error("Unknown Error!", exception);
            message = "내부 시스템 오류가 발생했습니다.";
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
        }

        FaultMessage faultMessage = new FaultMessage();
        faultMessage.setErrorCode(errorCode);
        faultMessage.setMessage(message);

        exchange.getMessage().setBody(faultMessage);
    }
}
