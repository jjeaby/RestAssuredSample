package ml.jjeaby.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.restassured.RestAssured;

public class AFirstWireMockTest {
	
	String bodyText = "You've reached a valid WireMock endpoint";

    
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8090);
	
	@Before
	public  void setup() {
	    RestAssured.baseURI = "http://localhost";
	    //RestAssured.basePath = "/an/endpoint";
	    RestAssured.port = 8090;
	}
	
	@Test
	public void testStatusCodePositive() {
		
		setupStub();

		RestAssured.
			 given().
			 when().
			         get("/an/endpoint").
			 then().
				assertThat().statusCode(200);
	}
	
	@Test
	public void testStatusCodeNegative() {
		
		setupStub();
		RestAssured.	
			given().
			when().
				get("/another/endpoint").
			then().
				assertThat().statusCode(401);
	}
	
	@Test
	public void testResponseContents() {
		
		setupStub();
		
		String response = 
				RestAssured.
					get("an/endpoint").asString();
		
		Assert.assertEquals(bodyText, response);
		
 
 
        
	}
	
	
	
	public void setupStub() {
		
		stubFor(
				get(urlEqualTo("/an/endpoint"))
					.willReturn(
						aResponse().withHeader("Content-Type", "text/plain")
									.withStatus(200)
									.withBody(bodyText))
	            );
		
		stubFor(
				get(urlEqualTo("/another/endpoint"))
					.willReturn(
							aResponse().withStatus(401))
	            );		
	}
}