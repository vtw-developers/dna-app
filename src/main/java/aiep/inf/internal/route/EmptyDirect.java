package aiep.inf.internal.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.spring.boot.SpringBootCamelContext;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * 아무 로직도 실행하지 않는 Camel Direct Route
 */
@Component(EmptyDirect.ROUTE_ID)
public class EmptyDirect extends EndpointRouteBuilder {

    public static final String ROUTE_ID = "EmptyDirect";

    @Override
    public void configure() throws Exception {
        from(direct(ROUTE_ID)).routeId(ROUTE_ID)
        .log(LoggingLevel.INFO, "EmptyDirect"); // 나중에 LoggingLevel Trace로 변경


//        rest("/user").description("User rest service")
//                .consumes("application/json").produces("application/json")
//                .get("/{id}").description("Find user by id").outType(User.class)
//                .param().name("id").type(path).description("The id of the user to get").dataType("int").endParam()
//                .to("bean:userService?method=getUser(${header.id})")
//                .put().description("Updates or create a user").type(User.class)
//                .param().name("body").type(body).description("The user to update or create").endParam()
//                .to("bean:userService?method=updateUser")
//                .get("/findAll").description("Find all users").outType(User[].class)
//                .to("bean:userService?method=listUsers");
    }
}
