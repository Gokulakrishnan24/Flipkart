package tests;

import api.ApiUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchProductTest {

    @Test(retryAnalyzer = utils.RetryAnalyzer.class)
    public void testSearchWithValidQuery() {
        System.out.println("✅ testSearchWithValidQuery started...");

        int maxAttempts = 3;
        int attempt = 0;
        Response response = null;

        while (attempt < maxAttempts) {
            response = ApiUtils.searchProduct("laptop"); // Valid query
            int statusCode = response.getStatusCode();

            if (statusCode == 429) {
                System.out.println("⚠️ Rate limit hit (429). Retrying after 2s...");
                attempt++;
                try {
                    Thread.sleep(2000); // wait for 2 seconds before retry
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break; // success or other error
            }
        }

        if (response == null || response.getStatusCode() == 429) {
            Assert.fail("❌ Received 429 - Too Many Requests after retries.");
        }

        System.out.println("✅ Response received: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
        Assert.assertTrue(response.getBody().asString().contains("laptop"), "Response does not contain expected product keyword");
    }
}