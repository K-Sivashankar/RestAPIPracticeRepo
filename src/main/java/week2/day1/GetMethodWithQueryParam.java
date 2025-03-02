package week2.day1;

import static io.restassured.RestAssured.*;

public class GetMethodWithQueryParam {

	public static void main(String[] args) {
		
		given()
		  .baseUri("https://dev262949.service-now.com")
		  .basePath("/api/now/table")
		  .auth()
		  .basic("admin", "vW0eDfd+A0V-")
		  .queryParam("sysparm_limit", "3")
		  .queryParam("sysparm_fields", "sys_id,state,description,short_description,number")
		  .log().all()
	    .when()
          .get("/incident")
        .then()
          .log().all()
          .assertThat()
          .statusCode(200);
		
	}

}