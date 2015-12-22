package com.capgemini.brahma.examples.route;

import com.capgemini.brahma.Application;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RouteIntegrationTests {

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void restAssuredHelloWorld() throws Exception {
        RestAssured.port = 18080; // TODO: this should be loaded from config
        when().get("/").then().body(equalTo("helloWorld"));
    }

    @Test
    public void restAssuredRestGetHello() throws Exception {
        RestAssured.port = 10000;  // TODO: this should be loaded from config
        when().get("/rest/hello").then().body(equalTo("\"BOOOOOOOM!!!!!\""));
    }

}
