package com.vtw.dna.internal;

import lombok.Data;

import java.util.Map;

@Data
public class FlowTemplateMeta {
    private String type;
    private String id;
    private String name;
    private Map<String, FlowTemplateParameter> parameters;
}
