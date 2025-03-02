package week2.day2;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ServiceNowE2ETest {
	
	String sysId;
	RequestSpecification requestSpecification;
	IncidentRequestBody incidentRequestBody;
	
	// AAA --> Arrange, Act and Assert
	
	// Arrange
	@BeforeClass
	public void beforeClass() {
		requestSpecification = new RequestSpecBuilder()
		    .setBaseUri("https://dev262949.service-now.com")
		    .setBasePath("/api/now/table/")
		    .setAuth(basic("admin", "vW0eDfd+A0V-"))
		    .addPathParam("tableName", "incident")
		    .addHeader("Content-Type", "application/json")
		    .build();
	}
	
	@Test(priority = 1)
	public void create_a_new_record() {
		incidentRequestBody = new IncidentRequestBody();
		incidentRequestBody.setDescription("Create new Record for E2E Test");
		incidentRequestBody.setShort_description("Service Now E2e Test");
		incidentRequestBody.setCategory("software");
		// Act
		sysId = given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()
		 .body(new Gson().toJson(incidentRequestBody))
		 .post("/{tableName}")
		// Assert
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(201)
		 .extract()
		 .jsonPath()
		 .getString("result.sys_id");
		
	}
	
	@Test(priority = 2)
	public void get_a_record() {
		given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()		 
		 .get("/{tableName}/"+sysId)
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(200)
		 .statusLine(Matchers.containsString("OK"))
		 .contentType(ContentType.JSON)
		 .body("result.sys_id", Matchers.equalTo(sysId));
	}
	
	@Test(priority = 3)
	public void update_a_record() {
		incidentRequestBody = new IncidentRequestBody();
		incidentRequestBody.setState("2");
		incidentRequestBody.setUrgency("1");
		
		given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()
	     .body(new Gson().toJson(incidentRequestBody))
		 .put("/{tableName}/"+sysId)
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(200)
		 .statusLine(Matchers.containsString("OK"))
		 .contentType(ContentType.JSON)
		 .body("result.sys_id", Matchers.equalTo(sysId))
		 .body("result.state", Matchers.equalTo(incidentRequestBody.getState()))
		 .body("result.urgency", Matchers.equalTo(incidentRequestBody.getUrgency()));
	}
	
	@Test(priority = 4)
	public void delete_a_record() {
		given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()		 
		 .delete("/{tableName}/"+sysId)
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(204)
		 .statusLine(Matchers.containsString("No Content"));
	}
	
	@Test(priority = 5)
	public void is_record_deleted_successfully() {
		given()
		 .spec(requestSpecification)
		 .log().all()
	   .when()		 
		 .get("/{tableName}/"+sysId)
		.then()
		 .log().all()
		 .assertThat()
		 .statusCode(404)
		 .statusLine(Matchers.containsString("Not Found"))
		 .contentType(ContentType.JSON);
	}

}