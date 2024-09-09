package aiep.inf.api.key.repository;

import aiep.inf.api.key.ApiKeySample;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ApiKeyRepository {
    ApiKeySample sample();
}
