package week3.day2.wiremock;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;

public class TestWireMock {
    public static void main(String[] args) {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"John\", \"age\": 30 }")
                .when()
                .post("http://localhost:8080/api/users")
                .then()
                .statusCode(200)
                .log().body();
    }
}
