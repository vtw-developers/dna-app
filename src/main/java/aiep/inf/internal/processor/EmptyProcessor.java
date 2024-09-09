package aiep.inf.internal.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/*
 * 아무 로직도 실행하지 않는 Camel Processor
 */
@Component("EmptyProcessor")
public class EmptyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
    }
}
