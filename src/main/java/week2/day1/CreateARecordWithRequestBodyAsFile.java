package week2.day1;

//import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;

import java.io.File;

public class CreateARecordWithRequestBodyAsFile {

	public static void main(String[] args) {		
		
		String sysId = RestAssured.given()
		  .baseUri("https://dev262949.service-now.com")
		  .basePath("/api/now/table")
		  .auth()
		  .basic("admin", "vW0eDfd+A0V-")
		  .pathParam("tableName", "incident")
		  .header("Content-Type", "application/json")
		  .log().all()
	   .when()
	      .body(new File("src/main/resources/incident_request_payload/CreateIncident.json"))
	      .post("/{tableName}")
	   .then()
	      .log().all()
	      .assertThat()
	      .statusCode(201)
	      .extract()
	      .jsonPath()
	      .getString("result.sys_id");
		
		System.out.println(sysId);

	}

}
