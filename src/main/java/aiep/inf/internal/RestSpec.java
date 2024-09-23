package aiep.inf.internal;

import lombok.Data;

import java.util.List;

@Data
public class RestSpec {
    private String id;
    private String httpMethod;
    private String path;
    private String tag;
    private String description;
    private List<RequestParameter> requestParameters;
    private DataSchema responseBody;
    private String outType;
    private Templated template;
}
