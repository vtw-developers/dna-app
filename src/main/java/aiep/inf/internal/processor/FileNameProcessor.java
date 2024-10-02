package aiep.inf.internal.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/*
 * fileName 처리 Camel Processor
 */
@Component("FileNameProcessor")
public class FileNameProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Object variableFileName = exchange.getVariable("download.fileName");
        if(variableFileName == null) {
            String content = exchange.getIn().getHeader("Content-Disposition", String.class);
            String fileName = content.replace("attachment;filename=", "");
            fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
            exchange.getMessage().setHeader(Exchange.FILE_NAME, fileName);
        }
        else {
            exchange.getMessage().setHeader(Exchange.FILE_NAME, variableFileName.toString());
        }
    }
}
