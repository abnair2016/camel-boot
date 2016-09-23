package com.capgemini.brahma.examples.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FTPRoute extends RouteBuilder {
    
    private static String QUESTION_MARK_SIGN = "?";
    private static String AMPERSAND_SIGN = "&";
        
    @Value("${camel.ftp.test.queue}")
    private String testQueue;
    
    @Value("${camel.ftp.server.url}")
    private String ftpUrl;
    
    @Value("${camel.ftp.server.username}")
    private String ftpUsername;
    
    @Value("${camel.ftp.server.password}")
    private String ftpPassword;
    
    @Value("${camel.ftp.server.disconnect.flag}")
    private String disconnectFlag;
    
    @Value("${camel.ftp.server.binary.flag}")
    private String binaryFlag;
    
    @Value("${camel.ftp.server.noop.flag}")
    private String noopFlag;
    
    @Override
    public void configure() throws Exception {
        
        from(buildFtpUrl())
            .log(LoggingLevel.INFO, "FTP-ing CSV file from Local FTP server to ActiveMQ queue:test.queue")
            .to(testQueue)
            .stop();
        
    }

    private String buildFtpUrl() {
        StringBuilder ftpUrlBuilder = new StringBuilder();
        ftpUrlBuilder.append(ftpUrl)
                        .append(QUESTION_MARK_SIGN)
                        .append("username=")
                        .append(ftpUsername)
                        .append(AMPERSAND_SIGN)
                        .append("password=")
                        .append(ftpPassword)
                        .append(AMPERSAND_SIGN)
                        .append("disconnect=")
                        .append(disconnectFlag)
                        .append(AMPERSAND_SIGN)
                        .append("binary=")
                        .append(binaryFlag)
                        .append(AMPERSAND_SIGN)
                        .append("noop=")
                        .append(noopFlag);
        return ftpUrlBuilder.toString().trim();
    }
}

