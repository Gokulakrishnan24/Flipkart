package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtils {

    public static Response searchProduct(String query) {
        return RestAssured.given()
                .baseUri("https://www.flipkart.com")
                .queryParam("q", query)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/114.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "en-US,en;q=0.5")
                .when()
                .get("/search");
    }
}
