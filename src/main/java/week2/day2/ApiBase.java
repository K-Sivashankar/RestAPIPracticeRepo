package week2.day2;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.basic;

public class ApiBase {
	
	protected String sysId;
	protected RequestSpecification requestSpecification;
	protected IncidentRequestBody incidentRequestBody;
	

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
	
	protected ResponseSpecification responseSpec(int statusCode, String statusLine, Long ms) {
		return new ResponseSpecBuilder()
		    .expectStatusCode(statusCode)
		    .expectStatusLine(Matchers.containsString(statusLine))
		    .expectContentType(ContentType.JSON)
		    .expectResponseTime(Matchers.lessThanOrEqualTo(ms))
		    .build();
	}
	//For DELETE method, we dont need Content type hence we create a separate method
	protected ResponseSpecification deleteResponseSpec(int statusCode, String statusLine, Long ms) {
		return new ResponseSpecBuilder()
		    .expectStatusCode(statusCode)
		    .expectStatusLine(Matchers.containsString(statusLine))		    
		    .expectResponseTime(Matchers.lessThanOrEqualTo(ms))
		    .build();
	}

}