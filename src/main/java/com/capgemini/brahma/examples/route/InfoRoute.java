package com.capgemini.brahma.examples.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Information HTTP Endpoint Route. A GET request returns a simple JSON service with information
 * on the service.
 *
 * @author - Nikhil Vibhav
 */
@Component
public class InfoRoute extends RouteBuilder {

    @Value("${rest.api.base.url}")
    private String restApiBaseUrl;

    @Value("${rest.api.port}")
    private String restApiPort;

    @Value("${project.info.name}")
    private String infoName;

    @Value("${project.info.description}")
    private String infoDescription;

    @Override
    public void configure() throws Exception {
        Map<String, String> infoMap = new HashMap<>();

        infoMap.put("name", infoName);
        infoMap.put("description", infoDescription);

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .host("0.0.0.0")
                .contextPath(restApiBaseUrl)
                .port(restApiPort);

        rest("/info").description("Info Endpoint")
                .produces("application/json")
                .get().route().transform().constant(infoMap);
    }
}
