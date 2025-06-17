package tests;

import api.ApiUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductTest {

    @Test
    public void testSearchWithValidQuery() throws InterruptedException {
        Thread.sleep(2000); // avoid 429

        String validQuery = "laptop";
        Response response = ApiUtils.searchProduct(validQuery);
        int statusCode = response.getStatusCode();

        if (statusCode == 429) {
            System.out.println("⚠️ Received 429 - Too Many Requests on valid query.");
            Assert.fail("Rate limit hit on valid query.");
        } else {
            Assert.assertEquals(statusCode, 200, "Expected 200 for valid query");
            Assert.assertTrue(response.getBody().asString().contains("laptop") || response.getBody().asString().length() > 100,
                    "Response body does not appear valid for 'laptop'");
        }
    }

    @Test
    public void testSearchWithInvalidQuery() throws InterruptedException {
        Thread.sleep(2000); // avoid 429

        String invalidQuery = "@@##" + System.currentTimeMillis();
        Response response = ApiUtils.searchProduct(invalidQuery);
        int statusCode = response.getStatusCode();

        if (statusCode == 429) {
            System.out.println("⚠️ Received 429 - Too Many Requests on invalid query.");
            Assert.fail("Rate limit hit on invalid query.");
        } else {
            Assert.assertEquals(statusCode, 200, "Expected 200 for invalid query");
            Assert.assertTrue(response.getBody().asString().length() > 0,
                    "Response body is unexpectedly empty for invalid query");
        }
    }
}
