package aiep.inf.api.key.processor;

import aiep.inf.api.key.ApiKeyChecker;
import aiep.inf.api.key.exception.InvalidApiKeyException;
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
        // GET Method 인 경우 Request Parameter는 Camel Message의 Header에 설정된다.
        String apiKey = exchange.getMessage().getHeader("apiKey", String.class);

        // Camel Processor에서 직접 로직을 구현하지 말고 다른 클래스 생성하여 구현
        // Camel Processor에서는 파라미터와 리턴 정의
        boolean isValid = apiKeyChecker.isValid(apiKey);
        if (isValid) {
            log.info("유효한 API 키입니다.");
        } else {
            throw new InvalidApiKeyException(apiKey);
        }
    }
}
