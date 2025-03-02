package week2.day2;

import static io.restassured.RestAssured.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.google.gson.Gson;

public class ServiceNowE2ETest2 extends ApiBase{
		
	@Test(priority = 1)
	public void creat_a_new_record() {	
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
		 .spec(responseSpec(201, "Created", 3500L))
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
		 .spec(responseSpec(200, "OK", 2500L))
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
		 .spec(responseSpec(200, "OK", 2500L))
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
		 .spec(deleteResponseSpec(204, "No Content", 2500L));
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
		 .spec(responseSpec(404, "Not Found", 1500L));
	}

}