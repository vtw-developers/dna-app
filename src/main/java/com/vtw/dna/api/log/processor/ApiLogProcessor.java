package com.vtw.dna.api.log.processor;

import com.vtw.dna.api.log.ApiLog;
import com.vtw.dna.api.log.ApiResult;
import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("ApiLogProcessor")
public class ApiLogProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String messageId = exchange.getExchangeId();
        LocalDateTime timestamp = LocalDateTime.now();

        // 결과, 에러메시지 설정
        ApiResult result = ApiResult.SUCCESS;
        String errorMessage = null;
        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        if (exception != null) {
            errorMessage = exception.getMessage();
            result = ApiResult.ERROR;
        }

        // 소요시간 설정
        List<MessageHistory> messageHistories = (List<MessageHistory>) exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
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

        exchange.getMessage().setBody(apiLog);
    }
}
