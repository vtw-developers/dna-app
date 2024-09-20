package aiep.inf.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "사용자 정보")
@Data
public class User {
    @Schema(example = "12345")
    private int id;

    @Schema(example = "홍길동")
    private String name;
}
