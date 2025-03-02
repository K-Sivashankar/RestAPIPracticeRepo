package week2.day1;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class GetAIncidentRecord {

	public static void main(String[] args) {
		String x="hello";
		RestAssured.baseURI="";
		RestAssured.basePath="";

		given()
		  .baseUri("https://dev262949.service-now.com")
		  .basePath("/api/now/table")
		  .auth()
		  .basic("admin", "vW0eDfd+A0V-")
		  .pathParam("tableName", "incident")
		  .pathParam("sysId", "46ee0924a9fe198100f1cf78c198454a")
//		  .log().all()
	    .when()
          .get("/{tableName}/{sysId}")
        .then()
          .log().all()
          .assertThat()
          .statusCode(200)
          .statusLine(containsString("OK"))
          .contentType(ContentType.JSON)
          .body("result.sys_id", equalTo("46ee0924a9fe198100f1cf78c198454a"));
		
	}

}