package com.capgemini.brahma.examples.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileMoveRoute extends RouteBuilder {
    
    @Value("${local.dir}")
    private String localDirectory;
    
    @Value("${remote.dir}")
    private String remoteDirectory;
    
    @Override
    public void configure() throws Exception {
        
        from(localDirectory)
            .log(LoggingLevel.INFO, "Moving CSV file from local directory to remote FTP upload directory...")
            .to(remoteDirectory)
            .stop();
    }
}

