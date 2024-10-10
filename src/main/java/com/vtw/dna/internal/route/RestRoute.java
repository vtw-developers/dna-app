package com.vtw.dna.internal.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vtw.dna.internal.*;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.rest.ParamDefinition;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Component
public class RestRoute extends EndpointRouteBuilder {

    @Value("${dna.meta-directory}")
    private Path metaDirectory;

    @Value("${dna.rest-api-directory}")
    private Path restApiDirectory;

    @Override
    public void configure() throws Exception {
        Map<String, FlowTemplateMeta> flowTemplateMetas = new HashMap<>();
        Files.find(metaDirectory,
                        Integer.MAX_VALUE,
                        (filePath, fileAttr) -> fileAttr.isRegularFile())
                .filter(path -> path.toString().endsWith(".template.yaml"))
                .forEach(path -> {
                    try {
                        String yaml = Files.readString(path);
                        FlowTemplateMeta flowTemplateMeta = new ObjectMapper(new YAMLFactory()).readValue(yaml, FlowTemplateMeta.class);
                        flowTemplateMetas.put(flowTemplateMeta.getId(), flowTemplateMeta);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });


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
                        if (requestParameters != null) {
                            for (RequestParameter requestParameter : requestParameters) {
                                ParamDefinition param = new ParamDefinition();
                                param.setName(requestParameter.getName());
                                param.setDataType(requestParameter.getType());
                                param.setRequired(requestParameter.isRequired());
                                param.setType(RestParamType.query);
                                params.add(param);
                            }
                        }

                        String produces = "application/json";
                        if (restSpec.getTemplate().getRef().contains("File")) {
                            produces = "application/octet-stream"; // Flow ID에 "File"이 포함된 경우(임시)
                        }

                        RestDefinition rest = rest()
                                .verb(restSpec.getHttpMethod().toLowerCase())
                                .tag(restSpec.getTag())
                                .id(restSpec.getId())
                                .path(restSpec.getPath())
                                .description(restSpec.getName())
                                .params(params)
                                .produces(produces)
                                .responseMessage("200", "successful operation")
                                .to("direct:" + restSpec.getTemplate().getRef());
                        if (restSpec.getOutType() != null) {
                            rest.outType(restSpec.getOutType());
                        }

                        Map<String, Object> parameters = restSpec.getTemplate().getParameters();
                        parameters.forEach((k, v) -> {
                            FlowTemplateMeta flowTemplateMeta = flowTemplateMetas.get(restSpec.getTemplate().getRef());
                            Map<String, FlowTemplateParameter> fParams = flowTemplateMeta.getParameters();
                            FlowTemplateParameter param = fParams.get(k);
                            if (param.getType().equals("object")) {
                                try {
                                    Map map = new ObjectMapper().readValue((String) v, Map.class);
                                    parameters.put(k, map);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                        getCamelContext().setVariable("route:" + restSpec.getId() + ":" + DnaExchange.TEMPLATE_PARAMETERS, parameters);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
