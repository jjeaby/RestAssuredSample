package ml.jjeaby.wiremock;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.restassured.RestAssured;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PingPongXmlTest {
	
	String okMessage = "<input>PING</input>";
	String faultMessage = "<input>PONG</input>";
		
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8090);
	
	@Before
	public  void setup() {
	    RestAssured.baseURI = "http://localhost";
	    //RestAssured.basePath = "/an/endpoint";
	    RestAssured.port = 8090;
	}
	
	
	@Test
	public void testPingPongPositive() {
		
		setupStub();
		
		RestAssured.
			given().
				body(okMessage).
			when().
				post("/pingpong").
			then().
				assertThat().
				statusCode(200).
				and().
				assertThat().body("output", org.hamcrest.Matchers.equalTo("PONG"));
	}
	
	@Test
	public void testPingPongNegative() {
		
		setupStub();
		
		RestAssured.
			given().
				body(faultMessage).
		        log().all().				
			when().
				post("/pingpong").
			then().
				assertThat().
				statusCode(404)
			.and().
				assertThat().body("output", org.hamcrest.Matchers.equalTo("PING"));
	}
	
	public void setupStub() {
		
		stubFor(
				post(urlEqualTo("/pingpong"))
					.withRequestBody(matching(okMessage))
					.willReturn(aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/xml")
	                .withBody("<output>PONG</output>"))
	            );
		
		stubFor(
				post(urlEqualTo("/pingpong"))
					.withRequestBody(matching(faultMessage))
					.willReturn(aResponse()
	                .withStatus(404)
	                .withHeader("Content-Type", "application/xml")
	                .withBody("<output>PING</output>"))
	            );		
	}
}
