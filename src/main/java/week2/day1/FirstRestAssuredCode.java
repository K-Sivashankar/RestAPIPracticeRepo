package week2.day1;

import io.restassured.RestAssured;

public class FirstRestAssuredCode {

	public static void main(String[] args) {
		
		// Pre-Condtion
		RestAssured.given()
		           .baseUri("https://dev262949.service-now.com")
		           .basePath("/api/now/table")
		           .auth()
		           .basic("admin", "vW0eDfd+A0V-")
		           // Request Log
		           .log().all()
		           // HTTP Action
		           .when()
		           .get("/incident")
		           // Assertion or Extraction
		           .then()
		           // Response Log
		           .log().all()
		           .assertThat()
		           .statusCode(200);

	}

}
