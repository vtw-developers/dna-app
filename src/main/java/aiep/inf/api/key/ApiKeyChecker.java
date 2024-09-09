package aiep.inf.api.key;

import aiep.inf.api.key.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.FluentProducerTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiKeyChecker {

    private final ApiKeyRepository apiKeyRepository;

    public boolean isValid(String apiKey) {
        // SQL 쿼리 실행 샘플 (MyBatis)
        ApiKeySample sample = apiKeyRepository.sample();
        log.info("Sample SQL: {}", sample);

        // TODO: API Key 검사 로직 구현
        return false;
    }
}
