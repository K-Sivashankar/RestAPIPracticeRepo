package week2.day1;

import static io.restassured.RestAssured.*;

public class CreateARecordWithRequestBodyAsString {

	public static void main(String[] args) {
		
		String request_body = """
				{
                 "description": "Create a new record using Post",
                 "short_description": "SDET SEP 2024"
                }				
				""";
		
		String sysId = given()
		  .baseUri("https://dev262949.service-now.com")
		  .basePath("/api/now/table")
		  .auth()
		  .basic("admin", "vW0eDfd+A0V-")
		  .pathParam("tableName", "incident")
		  .header("Content-Type", "application/json")
		  .log().all()
	   .when()
	      .body(request_body)
	      .post("/{tableName}")
	   .then()
	      .log().all()
	      .assertThat()
	      .statusCode(201)
	      .extract()
	      .jsonPath()
	      .getString("result.sys_id");
		
//		System.out.println(sysId);

	}

}
