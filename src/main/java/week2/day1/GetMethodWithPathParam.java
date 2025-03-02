package week2.day1;

import static io.restassured.RestAssured.*;

public class GetMethodWithPathParam {

	public static void main(String[] args) {
		
		given()
		  .baseUri("https://dev262949.service-now.com")
		  .basePath("/api/now/table")
		  .auth()
		  .basic("admin", "vW0eDfd+A0V-")
		  .pathParam("tableName", "incident")
		  .log().all()
	    .when()
          .get("/{tableName}")
        .then()
          .assertThat()
          .statusCode(200);
		
	}

}