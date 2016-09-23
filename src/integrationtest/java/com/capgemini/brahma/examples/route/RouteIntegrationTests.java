package com.capgemini.brahma.examples.route;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * An example integration test
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
public class RouteIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void restGetHelloTest() throws Exception {
        String body = this.restTemplate.getForObject("/api/v1/hello", String.class);
        assertThat(body).isEqualTo("\"BOOOOOOOM!!!!!\"");
    }
    
    @Test
    public void restGetInfoTest() throws Exception {
        Map<?,?> responseMap = this.restTemplate.getForObject("/api/v1/info", Map.class);
        assertNotNull(responseMap);
        assertThat(responseMap.get("name")).isEqualTo("Brahma Camel-Boot");
        assertThat(responseMap.get("description")).isEqualTo("A seed-project for starting new REST-API Camel/Spring Boot projects");
    }

}
