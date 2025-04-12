package InterviewQandA;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Forbidden403 {

    // Base URL of the API (using httpbin.org for demonstration)
    private static final String BASE_URL = "https://httpbin.org";

    // Simulated tokens for different roles
    private static final String ADMIN_TOKEN = "valid-admin-token";
    private static final String USER_TOKEN = "valid-user-token";

    // Endpoint that simulates a 403 response for unauthorized users
    private static final String ENDPOINT = "/status/403";

    @Test
    public void testForbiddenAccess() {
        System.out.println("Testing access to the endpoint...\n");

        // Case 1: Admin user tries to access the endpoint
        System.out.println("Case 1: Admin user access");
        Response responseAdmin = RestAssured.given()
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .when()
                .get(BASE_URL + ENDPOINT);

        System.out.println("Status Code: " + responseAdmin.getStatusCode());
        System.out.println("Response Body: " + responseAdmin.getBody().asString() + "\n");

        // Case 2: Regular user tries to access the endpoint
        System.out.println("Case 2: Regular user access");
        Response responseUser = RestAssured.given()
                .header("Authorization", "Bearer " + USER_TOKEN)
                .when()
                .get(BASE_URL + ENDPOINT);

        System.out.println("Status Code: " + responseUser.getStatusCode());
        System.out.println("Response Body: " + responseUser.getBody().asString() + "\n");

        // Validate the responses
        Assert.assertEquals(403, responseUser.getStatusCode(), "Expected 403 Forbidden status code");
        System.out.println("PASS: Received expected 403 Forbidden status code for unauthorized access.");
    }
}
