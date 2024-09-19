package aiep.inf.internal;

import lombok.Data;

import java.util.List;

@Data
public class RequestParameter {
    private String name;
    private boolean required;
    private String dataType;
}
