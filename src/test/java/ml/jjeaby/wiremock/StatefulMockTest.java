package ml.jjeaby.wiremock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

import io.restassured.RestAssured;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

public class StatefulMockTest  {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8090);
	
	@Before
	public  void setup() {
	    RestAssured.baseURI = "http://localhost";
	    //RestAssured.basePath = "/an/endpoint";
	    RestAssured.port = 8090;
	}
	
 	
	@Test
	public void testGetTodoList() {

		stubToGetTodoList();

		RestAssured
			.given()
			.when()
				.get("/todolist")
			.then()
				.assertThat().statusCode(200).
			and()
				.assertThat().body("list", org.hamcrest.Matchers.equalTo("Empty"));

	}

	@Test
	public void testPostTodoListAddItem() {	
		stubToPostTodoListAddItem();
		
		RestAssured
			.given()
			.when()
				.post("/todolist")
			.then()
				.assertThat().statusCode(201);
	}
	
	@Test
	public void testGetTodoListAddItem() {		
		stubToGetTodoListAddItem();
		
		RestAssured
			.given()
			.when()
				.get("/todolist")
			.then()
				.assertThat().statusCode(200)
			.and()
				.assertThat().body("list", org.hamcrest.Matchers.not("Empty"))
			.and()
				.assertThat().body("list.item", org.hamcrest.Matchers.equalTo("Item added to list"));

	}

	
	
	
	public void stubToGetTodoList() {

		stubFor(
				get(urlEqualTo("/todolist")).inScenario("addItem").
				whenScenarioStateIs(Scenario.STARTED).
				willReturn(
						aResponse().
							withStatus(200).
							withHeader("Content-Type", "application/xml").
							withBody("<list>Empty</list>"))
				);
	}
	public void stubToPostTodoListAddItem() {

		stubFor(
				post(urlEqualTo("/todolist")).inScenario("addItem").
				whenScenarioStateIs(Scenario.STARTED).
					willSetStateTo("itemAdded").
					willReturn(
						aResponse().
							withHeader("Content-Type", "application/xml").
							withStatus(201))
				);
		
		
		

	}
	public void stubToGetTodoListAddItem() {
		
		stubFor(
				get(urlEqualTo("/todolist")).inScenario("addItem").
				whenScenarioStateIs(Scenario.STARTED).
					willSetStateTo("itemAdded").
					willReturn(
							aResponse().
								withStatus(200).
								withHeader("Content-Type", "application/xml").
								withBody("<list><item>Item added to list</item></list>"))
				);

	}

}
