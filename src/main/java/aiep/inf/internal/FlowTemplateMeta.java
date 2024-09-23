package aiep.inf.internal;

import lombok.Data;

import java.util.Map;

@Data
public class FlowTemplateMeta {
    private String name;
    private String templateId;
    private Map<String, FlowTemplateParameter> parameters;

}
