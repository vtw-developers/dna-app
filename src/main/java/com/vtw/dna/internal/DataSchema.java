package com.vtw.dna.internal;

import lombok.Data;

import java.util.Map;

@Data
public class DataSchema {
    private String type;
    private Map<String, DataSchema> properties;
    private DataSchema items;
    private String description;
    private String example;
}
