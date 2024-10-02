package com.vtw.dna.api.key.repository;

import com.vtw.dna.api.key.ApiKeySample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiKeyRepository {
    ApiKeySample sample();
}
