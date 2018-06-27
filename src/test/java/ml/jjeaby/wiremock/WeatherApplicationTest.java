package ml.jjeaby.wiremock;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.PrintStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.get;


public class WeatherApplicationTest {
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8090);

	
 
	
	@Before
	public void steup() {
		
 
         
 
		RestAssured.baseURI = "http://localhost";
	    //RestAssured.basePath = "/an/endpoint";
	    RestAssured.port = 8090;
	    
		RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
		requestBuilder.addCookie("my_cookie", "mycookievalue");
		requestBuilder.setContentType("application/json");
		RestAssured.requestSpecification = requestBuilder.build();
		
	}
	
	

	@Test
	public  void test1() {

		stubFor(
				get(urlEqualTo("/forecast/51.507253-0.127755"))
				.willReturn(
						aResponse()
							.withHeader("Content-Type", "application/json")
							.withStatus(200)
							.withBody("{\"currently\":{\"windSpeed\":12.34}}"))
					);
		
		
		
		Response res = RestAssured
					.given()
						.pathParam("GPSLocationX", "51.507253")
						.pathParam("GPSLocationY", "-0.127755")
				        .log().all()

					.when()
						.get("/forecast/{GPSLocationX}{GPSLocationY}")
					.then()
						.statusCode(200)
						.body("currently.windSpeed", is(12.34f))
						.extract()
						.response();
		
		res.prettyPrint();
		
	}
	
	@Test
	public  void test2() {

		stubFor(
				get(urlEqualTo("/forecast?GPSLocationX=51.507253&GPSLocationY=-0.127755"))
		//				get(urlEqualTo("/forecast/51.507253.-0.127755"))
				.willReturn(
						aResponse()
							.withHeader("Content-Type", "application/json")
							.withStatus(200)
							.withBody("{\"currently\":{\"windSpeed\":12.34}}"))
					);
 

		
		RestAssured
			.given().
			        param("GPSLocationX", "51.507253").
			        param("GPSLocationY", "-0.127755").
			        log().all().
			when().
			        get("/forecast").
			then().
			        statusCode(200).
			        body("currently.windSpeed", is(12.34f));
	}
	
	@Test
	public  void test3() {

		stubFor(get(urlEqualTo("/forecast/cookies"))
		            .withCookie("my_cookie", containing("mycookievalue"))
		            .willReturn(
		            		aResponse()
							.withHeader("Content-Type", "application/text/html")
							.withStatus(200))
		            );
  
		

		RestAssured
			.given()
					.log().all()
			.when()
				.get("/forecast/cookies")
			.then()
				.statusCode(200)
				.extract().response();
		  
	}
}

