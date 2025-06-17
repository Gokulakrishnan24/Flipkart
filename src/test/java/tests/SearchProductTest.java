package tests;

import api.ApiUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductTest {

    @Test
    public void testSearchWithValidProduct() {
        String searchTerm = "laptop";
        Response response = ApiUtils.searchProduct(searchTerm);

        String responseBody = response.getBody().asString();

        // Example validations
        Assert.assertTrue(responseBody.contains("Laptop") || responseBody.contains("laptop"),
                "Search results do not contain expected product.");

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch.");
    }

    @Test
    public void testSearchWithInvalidQuery() {
        String searchTerm = "@@##";
        Response response = ApiUtils.searchProduct(searchTerm);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should still be 200 for invalid search.");
        Assert.assertTrue(response.getBody().asString().contains("Showing results for")
                        || response.getBody().asString().contains("did not match"),
                "No fallback message found.");
    }
}
