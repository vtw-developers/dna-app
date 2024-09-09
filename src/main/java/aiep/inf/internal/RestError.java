package aiep.inf.internal;

import lombok.Data;

@Data
public class RestError {
    private String exception;
    private String errorCode;
    private String message;
    private int httpStatus;
}
