package week2.day1;

import org.hamcrest.Matchers;

import static io.restassured.RestAssured.*;

public class DeleteExistingIncidentRecord {

	public static void main(String[] args) {
		
		given()
		  .baseUri("https://dev262949.service-now.com")
		  .basePath("/api/now/table")
		  .auth()
		  .basic("admin", "vW0eDfd+A0V-")
		  .pathParam("tableName", "incident")
		  .pathParam("sysId", "46ee0924a9fe198100f1cf78c198454a")
		  .log().all()
	    .when()
          .delete("/{tableName}/{sysId}")
        .then()
          .log().all()
          .assertThat()
          .statusCode(404)
          .statusLine(Matchers.containsString("No DContent"));
		
	}

}