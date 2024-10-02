package com.vtw.dna.internal.route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.vtw.dna.internal.DnaExchange;
import com.vtw.dna.internal.FlowTemplateMeta;
import com.vtw.dna.internal.FlowTemplateParameter;
import com.vtw.dna.internal.RestSpec;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class BatchRoute extends EndpointRouteBuilder {

    @Value("${dna.meta-directory}")
    private Path metaDirectory;

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


        Files.find(metaDirectory,
            Integer.MAX_VALUE,
            (filePath, fileAttr) -> fileAttr.isRegularFile())
          .filter(path -> path.toString().endsWith(".batch.yaml"))
          .forEach(path -> {
              try {
                  String yaml = Files.readString(path);
                  RestSpec restSpec = new ObjectMapper(new YAMLFactory()).readValue(yaml, RestSpec.class);

                  from("direct:" + restSpec.getId()).id(restSpec.getId())
                          .setVariable("routeId", constant(restSpec.getId()))
                          .process("TemplateParametersToVariableProcessor")
                          .to("direct:" + restSpec.getTemplate().getRef());

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
