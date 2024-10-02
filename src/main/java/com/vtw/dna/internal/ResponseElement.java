package com.vtw.dna.internal;

import lombok.Data;

import java.util.List;

@Data
public class ResponseElement {
    private String name;
    private String dataType;
    private List<ResponseElement> children;
}
