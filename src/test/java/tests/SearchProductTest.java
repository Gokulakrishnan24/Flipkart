package tests;

import api.ApiUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductTest {

    @Test
    public void testSearchWithInvalidQuery() throws InterruptedException {
        // Adding slight delay to reduce chances of 429
        Thread.sleep(2000);

        String invalidQuery = "@@##" + System.currentTimeMillis();  // Random invalid input to avoid duplicate detection
        Response response = ApiUtils.searchProduct(invalidQuery);
        int statusCode = response.getStatusCode();

        if (statusCode == 429) {
            System.out.println("⚠️ Received 429 - Too Many Requests. Try throttling or add headers.");
            Assert.fail("API rate limit hit (429). Consider delay or header tweaks.");
        } else {
            Assert.assertEquals(statusCode, 200, "Expected status code 200 for invalid query");
        }
    }
}
