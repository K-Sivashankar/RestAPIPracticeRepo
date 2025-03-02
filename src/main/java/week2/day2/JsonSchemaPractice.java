package week2.day2;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.*;

import java.io.File;

public class JsonSchemaPractice {

    public static void main(String[] args) {

        Response response = RestAssured.given()
                .baseUri("https://dev262949.service-now.com")
                .basePath("/api/now/table")
                .auth()
                .basic("admin", "vW0eDfd+A0V-")
                .log()//RequestLogSpecification
                .all()//RequestSpecification
                .when()//RequestSpecification
                .get("/incident");//Response

//Called method belongs to Class/subclass of calling method's return type
ValidatableResponse validatableResponse = response
        .then()//ValidatableResponse
        .log()//ValidatableResponseLogSpec
        .all();//ValidatableResponse
validatableResponse.assertThat().
body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/resources/Schema.json")));
    }
}
