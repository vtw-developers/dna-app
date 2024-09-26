package aiep.inf.internal;

import lombok.Data;

@Data
public class RequestParameter {
    private String name;
    private boolean required;
    private String type;
}
