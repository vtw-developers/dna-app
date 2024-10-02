package com.vtw.dna.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "true 또는 false")
@Data
public class BooleanApiResponse {
    @Schema(example = "true")
    private boolean result;
}
