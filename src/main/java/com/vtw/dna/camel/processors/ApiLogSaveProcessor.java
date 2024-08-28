package com.vtw.dna.camel.processors;

import com.vtw.dna.camel.ApiLog;
import com.vtw.dna.camel.ServiceResult;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.sql;

@AllArgsConstructor
@Component("ApiLogSaveProcessor")
public class ApiLogSaveProcessor implements Processor {

    private final FluentProducerTemplate producerTemplate;

    @Override
    public void process(Exchange exchange) throws Exception {
        String messageId = exchange.getExchangeId();
        LocalDateTime timestamp = LocalDateTime.now();

        ServiceResult result = ServiceResult.SUCCESS;
        String errorMessage = null;
        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        if (exception != null) {
            errorMessage = exception.getMessage();
            result = ServiceResult.ERROR;
        }
        List<MessageHistory> messageHistories = (List<MessageHistory>)exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
        Long elapsedTime = messageHistories
                .stream().map(h -> h.getElapsed()).reduce(0L, Long::sum);

        ApiLog apiLog = ApiLog.builder()
                .messageId(messageId)
                .timestamp(timestamp)
                .flowId(exchange.getFromRouteId())
                .result(result)
                .errorMessage(errorMessage)
                .elapsedTime(elapsedTime)
                .build();

        Map<String, Object> params = new HashMap<>();
        params.put("messageId", apiLog.getMessageId());
        params.put("timestamp", apiLog.getTimestamp());
        params.put("flowId", apiLog.getFlowId());
        params.put("result", apiLog.getResult().getCode());
        params.put("errorMessage", apiLog.getErrorMessage());
        params.put("elapsedTime", apiLog.getElapsedTime());

        String sql = "insert into api_log (message_id, \"timestamp\", flow_id, result, error_message, elapsed_time)" +
                " values (:#messageId, :#timestamp, :#flowId, :#result, :#errorMessage, :#elapsedTime)";

        producerTemplate.withBody(params)
                .to(sql(sql))
                .request();
    }
}
