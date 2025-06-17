package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtils {
    public static Response searchProduct(String query) {
        return RestAssured.given()
                .baseUri("https://www.flipkart.com")
                .queryParam("q", query)
                .header("User-Agent", "Mozilla/5.0")
                .when()
                .get("/search");
    }
}