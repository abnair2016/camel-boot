package com.capgemini.brahma.examples.route;

import static com.capgemini.brahma.util.CamelBootUtil.getUser;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.capgemini.brahma.dao.UserRepository;
import com.capgemini.brahma.model.User;

@Component
public class DBRoute extends RouteBuilder {

    @Autowired
    private UserRepository userRepository;
    
    @Value("${camel.ftp.test.queue}")
    private String testQueue;
    
    @Override
    public void configure() throws Exception {
        from(testQueue)
            .transacted()
            .threads(10)
            .log(LoggingLevel.INFO, "Persisting User in DB")
            .process(exchange -> {
                User user = getUser(exchange.getIn().getBody().toString());
                int userStoredInDatabase = userRepository.store(user);
                if(userStoredInDatabase==1){
                    log.info("User Stored In Database:: SUCCESS!");
                }
            })
            .to("mock:results");

    }
}
