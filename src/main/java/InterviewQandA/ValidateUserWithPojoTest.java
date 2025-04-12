package InterviewQandA;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.*;

public class ValidateUserWithPojoTest {

    @Test
    public void validateUsingPojo() {
        RestAssured.baseURI = "https://api.example.com";

        Response response = given()
                .header("Authorization", "Bearer <your_token>")
                .when()
                .get("/users/101")
                .then()
                .statusCode(200)
                .extract().response();

        UserResponse user = response.as(UserResponse.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(user.getId(), 101, "Mismatch: id");
        softAssert.assertEquals(user.getName(), "Siva", "Mismatch: name");
        softAssert.assertEquals(user.getEmail(), "siva@example.com", "Mismatch: email");
        softAssert.assertEquals(user.getStatus(), "active", "Mismatch: status");
        softAssert.assertEquals(user.getRole(), "admin", "Mismatch: role");

        // Add assertions up to 40 fields here...

        softAssert.assertAll();
    }
}

