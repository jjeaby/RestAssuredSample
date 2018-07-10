package ml.jjeaby.RestAssuredSample;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.DEFINED_PORT)
public class BookRestApiTest {


    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081;
    }


    @Test
    public void testIndex() {


        RestAssured.
                given().
                when().
                    get("/").
                then().
                    assertThat().
                    statusCode(200);
    }

    @Test
    public void testBookInfo() {
        Book book = new Book();
        book.setTitle("REST API TEST");
        book.setWriter("Jin");
        book.setPublisher("ACT 출판사");
        book.setStatus("instock");

        RestAssured
                .given()
                    .pathParam("BookTitle", book.getTitle())
                    .log().all()
                .when()
                    .get("/rest/info/{BookTitle}")
                .then()
                    .log().all()
                    .body("title", is("REST API TEST"))
                    .body("writer", is("Jin"))
                    .body("publisher", is("ACT 출판사"))
                    .body("status", is("instock"))
                    .statusCode(200)
                    .extract().response();


    }
}