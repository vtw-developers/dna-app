package com.vtw.dna.camel;

import java.util.Arrays;

public enum ServiceResult {
    SUCCESS("S"), ERROR("E");

    private String code;

    private ServiceResult(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ServiceResult getByCode(String code) {
        return Arrays.stream(values()).filter(value->value.getCode().equals(code)).findFirst().orElseThrow();
    }
}
