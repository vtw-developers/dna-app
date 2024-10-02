package com.vtw.dna.internal;

import lombok.Data;

import java.util.Map;

@Data
public class FlowTemplateParameter {
    private String type;
    private String description;
    private String defaultValue;
}
