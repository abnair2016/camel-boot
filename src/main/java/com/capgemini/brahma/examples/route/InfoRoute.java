package com.capgemini.brahma.examples.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * "Info" HTTP Endpoint Route. GET returns a simple JSON service with infomation
 * on the service
 *
 * @author - Nikhil Vibhav
 */
@Component
public class InfoRoute extends RouteBuilder {

    @Value("${rest.api.base.url}")
    private String restApiBaseUrl;

    @Value("${rest.api.port}")
    private String restApiPort;

    @Override
    public void configure() throws Exception {
        Map<String, String> infoMap = new HashMap<>();

        String infoName = "Brahma Camel-Boot";
        String infoDescription = "A seed-project for starting new REST-API Camel/Spring Boot projects";

        infoMap.put("name", infoName);
        infoMap.put("description", infoDescription);

        restConfiguration()
                .component("netty4-http")
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
