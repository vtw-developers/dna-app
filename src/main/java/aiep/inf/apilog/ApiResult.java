package aiep.inf.apilog;

import java.util.Arrays;

public enum ApiResult {
    SUCCESS("S"), ERROR("E");

    private String code;

    private ApiResult(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ApiResult getByCode(String code) {
        return Arrays.stream(values()).filter(value->value.getCode().equals(code)).findFirst().orElseThrow();
    }
}
