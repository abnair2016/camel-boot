package com.capgemini.brahma.config;

import com.capgemini.archaius.spring.ArchaiusBridgePropertyPlaceholderConfigurer;
import com.capgemini.brahma.examples.route.ActiveMQRoute;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * Spring Configuration class which provides all the app-specific config via @Bean-annotated methods.
 */
@Configuration
public class MyAppConfig {

    @Bean
    public PooledConnectionFactory getPooledConnectionFactory() {
        return new PooledConnectionFactory(new ActiveMQConnectionFactory());
    }
    
    protected void addActiveMQComponentToContext(CamelContext context) throws Exception {
        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConnectionFactory(getPooledConnectionFactory());
        context.addComponent("activemq", activeMQComponent);
        context.addRoutes(new ActiveMQRoute());
    }
    
    /**
     * Access the camel context.
     * @return the camel context
     */
    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext context) {
                System.out.println("your custom configuration goes here.");
                PropertiesComponent pc = new PropertiesComponent();
                pc.setLocation("file:config/env.properties");
                context.addComponent("properties", pc);

                if(context.isAllowUseOriginalMessage()) {
                    context.setAllowUseOriginalMessage(Boolean.FALSE);
                }
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) { 
            }
        };
    }

    /**
     * Setup the ArchaiusBridgePropertyPlaceholderConfigurer to provide properties information to Spring, Camel, and
     * Hystrix.
     * @return the configurer
     */
    @Bean
    @Primary
    public ArchaiusBridgePropertyPlaceholderConfigurer bridgePropertyPlaceholder() {

        ArchaiusBridgePropertyPlaceholderConfigurer configurer = new ArchaiusBridgePropertyPlaceholderConfigurer();

        configurer.setIgnoreResourceNotFound(true);
        configurer.setInitialDelayMillis(5000);
        configurer.setDelayMillis(5000);
        configurer.setIgnoreDeletesFromSource(true);
        Resource[] resources = new Resource[6];
        resources[0] = new FileSystemResource("config/application.properties");
        resources[1] = new FileSystemResource("config/env.properties");
        resources[2] = new FileSystemResource("config/filemove.properties");
        resources[3] = new FileSystemResource("config/ftp.properties");
        resources[4] = new FileSystemResource("config/hystrix.properties");
        resources[5] = new FileSystemResource("config/mq.properties");
        configurer.setLocations(resources);
        return configurer;
    }
}
