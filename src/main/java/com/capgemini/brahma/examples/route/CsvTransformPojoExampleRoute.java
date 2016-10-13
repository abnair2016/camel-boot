package com.capgemini.brahma.examples.route;

import static com.capgemini.brahma.util.CamelBootUtil.AMPERSAND_SIGN;
import static com.capgemini.brahma.util.CamelBootUtil.QUESTION_MARK_SIGN;

import org.apache.camel.Message;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.capgemini.brahma.model.User;

@Component
public class CsvTransformPojoExampleRoute extends RouteBuilder {
        
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
        
        BindyCsvDataFormat bindyCsvDataFormat = new BindyCsvDataFormat(com.capgemini.brahma.model.User.class);
        bindyCsvDataFormat.setLocale("default");
        
        from(buildFtpUrl())
            .log(LoggingLevel.INFO, "Unmarshalling CSV using Bindy...")
            .unmarshal(bindyCsvDataFormat)
            .split(body())
            .process(exchange -> {
                Message in = exchange.getIn();
                User user = (User)in.getBody();
                in.setBody(user.getFullName().toUpperCase());
                log.info("Exchange{}:: " + user.getFullName().toUpperCase());
            })
            .to(testQueue);
    }

    private String buildFtpUrl() {
        StringBuilder ftpUrlBuilder = new StringBuilder();
        return ftpUrlBuilder.append(ftpUrl)
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
                            .append(noopFlag)
                            .toString().trim();
    }
}

