package InterviewQandA;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ResponseValidation {

    @Test
    public void validateMultipleFields() {
        RestAssured.baseURI = "https://api.example.com";

        Response response = given()
                .header("Authorization", "Bearer <your_token>")
                .when()
                .get("/users/101")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        // You can hardcode or load from file
        Map<String, Object> expectedFields = Map.of(
                "id", 101,
                "name", "Siva",
                "email", "siva@example.com",
                "status", "active",
                "role", "admin"
                // ... up to 40 fields
        );



        SoftAssert softAssert = new SoftAssert();

        for (Map.Entry<String, Object> entry : expectedFields.entrySet()) {
            Object actual = jsonPath.get(entry.getKey());
            softAssert.assertEquals(actual, entry.getValue(), "Mismatch at: " + entry.getKey());
        }

        softAssert.assertAll();
    }
}

