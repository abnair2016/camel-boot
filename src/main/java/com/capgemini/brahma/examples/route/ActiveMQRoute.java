package com.capgemini.brahma.examples.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.capgemini.brahma.model.User;

@Component
public class ActiveMQRoute extends RouteBuilder {

    @Value("${camel.in.queue.name}")
    private String inQueue;

    @Value("${camel.out.queue.name}")
    private String outQueue;
    
    @Value("${camel.json.file.input}")
    private String jsonFileInput;

    @Override
    public void configure() throws Exception {
        
        from(jsonFileInput)
            .log(LoggingLevel.INFO, "Copying from local JSON file to ActiveMQ queue:in")
            .to(inQueue);
        
        from(inQueue)
            .log(LoggingLevel.INFO, "Unmarshalling JSON body from queue:in...")
            .unmarshal().json(JsonLibrary.Jackson, User.class)
            .process(exchange -> {
                User user = exchange.getIn().getBody(User.class);
                exchange.getIn().setBody(user.toString());
                log.info("User Extracted from JSON:: " + user.toString());
            })
            .log(LoggingLevel.INFO, "Moving User's full name to ActiveMQ queue:out for further processing or consumption")
            .to(outQueue)
            .stop();
    }
}

