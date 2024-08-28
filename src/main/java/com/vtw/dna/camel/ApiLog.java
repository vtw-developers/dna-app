package com.vtw.dna.camel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ApiLog {
    private String messageId;
    private String flowId;
    private LocalDateTime timestamp;
    private ServiceResult result;
    private String errorCode;
    private String errorMessage;
    private String flowName;
    private String details;
    private long elapsedTime;
}
