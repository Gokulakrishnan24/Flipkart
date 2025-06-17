package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

public class ApiUtils {

    public static Response searchProduct(String query) {
        RequestSpecification request = RestAssured.given()
                .baseUri("https://www.flipkart.com")
                .queryParam("q", query)
                .header("User-Agent", "Mozilla/5.0")
                .log().all(); // Logs the full request

        Response response = request
                .when()
                .get("/search");

        response.then()
                .log().all() // Logs the full response
                .statusCode(200) // Ensure API is healthy
                .time(lessThan(3000L)); // Ensure response is quick (under 3 sec)

        return response;
    }
}
