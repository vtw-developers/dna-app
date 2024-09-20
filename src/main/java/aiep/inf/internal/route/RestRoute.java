package aiep.inf.internal.route;

import aiep.inf.api.dto.User;
import aiep.inf.internal.DnaExchange;
import aiep.inf.internal.RequestParameter;
import aiep.inf.internal.RestSpec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.rest.ParamDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.model.rest.RestParamType;
import org.apache.catalina.util.URLEncoder;
import org.apache.hc.core5.net.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class RestRoute extends EndpointRouteBuilder {

    @Value("${dna.rest-api-directory}")
    private Path restApiDirectory;

    @Override
    public void configure() throws Exception {
        Files.find(restApiDirectory,
                        Integer.MAX_VALUE,
                        (filePath, fileAttr) -> fileAttr.isRegularFile())
                .filter(path -> path.toString().endsWith(".rest.yaml"))
                .forEach(path -> {
                    try {
                        String yaml = Files.readString(path);
                        RestSpec restSpec = new ObjectMapper(new YAMLFactory()).readValue(yaml, RestSpec.class);

                        List<ParamDefinition> params = new ArrayList<>();
                        List<RequestParameter> requestParameters = restSpec.getRequestParameters();
                        for (RequestParameter requestParameter : requestParameters) {
                            ParamDefinition param = new ParamDefinition();
                            param.setName(requestParameter.getName());
                            param.setDataType(requestParameter.getDataType());
                            param.setRequired(requestParameter.isRequired());
                            param.setType(RestParamType.query);
                            params.add(param);
                        }

                        RestDefinition rest = rest()
                                .verb(restSpec.getHttpMethod().toLowerCase())
                                .tag(restSpec.getTag())
                                .id(restSpec.getId())
                                .path(restSpec.getPath())
                                .description(restSpec.getDescription())
                                .params(params)
                                .produces("application/json")
                                .responseMessage("200", "successful operation")
                                .to("direct:" + restSpec.getTemplate().getRef());
                        if (restSpec.getOutType() != null) {
                            rest.outType(restSpec.getOutType());
                        }

                        Map<String, Object> parameters = Collections.unmodifiableMap(restSpec.getTemplate().getParameters());
                        getCamelContext().setVariable("route:" + restSpec.getId() + ":" + DnaExchange.TEMPLATE_PARAMETERS, parameters);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
