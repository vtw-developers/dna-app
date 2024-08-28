package com.vtw.dna.edu;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LcmsCallProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("LCMS HTTP 요청");
    }
}
