package com.vtw.dna.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.sql;

@Slf4j
@RequiredArgsConstructor
@Component
@DisallowConcurrentExecution
public class FlowScheduleJob extends QuartzJobBean {

    private final FluentProducerTemplate producerTemplate;

    @Value("${spring.datasource.driver-class-name}")
    private String driverName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean("adminDatasource")
    public DataSource getDatasource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String app = "";
        try {
            app = context.getScheduler().getSchedulerName();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        String flow = context.getJobDetail().getKey().getName();
        String result = "SUCCESS";
        String errorMessage = "";
        LocalDateTime start = LocalDateTime.now();
        log.info("Quartz Schedule Job Started. Flow: {} StartTime: {}", flow, start);

        Exchange resultExchange = producerTemplate.to(direct(flow)).send();
        Exception exception = resultExchange.getException();
        if (exception != null) {
            result = "FAIL";
            errorMessage = exception.getMessage();
            log.error("Schedule Error. Flow: {} DateTime: {}, Error: {}", flow, LocalDateTime.now(), errorMessage);
            throw new JobExecutionException(exception);
        }

        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        log.info("Quartz Schedule Job Completed. Flow: {} EndTime: {}, Duration: {}s", flow, end, duration.getSeconds());
    }
}
