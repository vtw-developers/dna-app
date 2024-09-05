package aiep.inf.apikey.processors;

import aiep.inf.apikey.ApiKeyChecker;
import aiep.inf.apikey.InvalidApiKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("ApiKeyCheckProcessor")
public class ApiKeyCheckProcessor implements Processor {

    private final ApiKeyChecker apiKeyChecker;

    @Override
    public void process(Exchange exchange) throws Exception {

        String apiKey = exchange.getMessage().getHeader("apiKey", String.class);

        boolean isValid = apiKeyChecker.isValid(apiKey);
        if (isValid) {
            log.info("유효한 API 키입니다.");
        } else {
            throw new InvalidApiKeyException(apiKey);
        }
    }
}
