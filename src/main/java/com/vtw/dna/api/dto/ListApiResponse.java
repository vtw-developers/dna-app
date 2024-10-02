package com.vtw.dna.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "데이터의 구조가 명시되지 않은 목록 정보")
@Data
public class ListApiResponse {
    @Schema(example = "12345678")
    private long totalCount;

    private List data;
}
