package week2.day1;

import static io.restassured.RestAssured.*;

public class CreateARecordWithRequestBodyAsPojoObject {
	
	public static void main(String[] args) {	
		
		IncidentRequestPayload request_payload = new IncidentRequestPayload();
		
		request_payload.setDescription("Create Description using POJO class");
		request_payload.setShort_description("SDETSEP2024");
		
		String sysId = given()
		  .baseUri("https://dev262949.service-now.com")
		  .basePath("/api/now/table")
		  .auth()
		  .basic("admin", "vW0eDfd+A0V-")
		  .pathParam("tableName", "incident")
		  .header("Content-Type", "application/json")
		  .log().all()
	   .when()
	      .body(request_payload)
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
