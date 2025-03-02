package week2.day2;

import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;

public class GetARecordJsonSchemaValidationAsFile {

	public static void main(String[] args) {
		given()
		  .baseUri("https://dev262949.service-now.com")
		  .basePath("/api/now/table")
		  .auth()
		  .basic("admin", "vW0eDfd+A0V-")
		  .pathParam("tableName", "incident")
		  .pathParam("sysId", "c14e4b2a83631e10695bc7b6feaad369")
		  .log().all()
	    .when()
        .get("/{tableName}/{sysId}")
          .then()
          .log().all()
        .assertThat()
        .statusCode(200)
        .statusLine(Matchers.containsString("OK"))
        .contentType(ContentType.JSON)
        .body("result.sys_id", Matchers.equalTo("c14e4b2a83631e10695bc7b6feaad369"))
        .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/resources/GetARecordJsonSchema.json")));
		
	}

}
