package com.vtw.dna.internal;

import lombok.Data;

import java.util.Map;

@Data
public class Templated {
    private String ref;
    private Map<String, Object> parameters;
}
