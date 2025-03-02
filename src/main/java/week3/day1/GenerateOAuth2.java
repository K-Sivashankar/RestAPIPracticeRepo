package week3.day1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GenerateOAuth2 {
    static String access_token = "";

    @Test(priority = 1)
    public void generateOAuth2() {

        access_token =  RestAssured.given()
                .header("Content-Type","application/x-www-form-urlencoded").baseUri("https://dev223408.service-now.com")
                .basePath("/oauth_token.do")
                .formParam("grant_type", "password")
                .formParam("client_id", "8c00161774434144b8bbf0e87f0ec35f")
                .formParam("client_secret", "]8uF}NpOco")
                .formParam("username", "admin")
                .formParam("password", "n%Y1=bbDb9NB").log().all()
                .when().post()
                .then().log().all()
                .assertThat()
                .statusCode(200).extract()
                .body()
                .jsonPath()
                .getString("access_token");
        System.out.println(access_token);

    }

    @Test(dependsOnMethods = {"generateOAuth2"})
    public void postUsingOAuthToken() {
        Response response = RestAssured
                .given()
                .baseUri("https://dev223408.service-now.com")
                .basePath("/api/now/table/change_request")
                .header("Content-Type", "application/json")
                .auth()
                .oauth2(GenerateOAuth2.access_token)
                .body("""
                        {
                        "description":"Updated Feb 16"
                        }
                        """)
                .log().all()
                .when().post();

        String description = response.then().log().all()
                .assertThat().statusCode(201).extract()
                .body().jsonPath().getString("result.description");

        Assert.assertEquals(description, "Updated Feb 16");

    }
}

//Mistakes I did
//gave wrong json path in getString method
//missed header content type
//gave wrong spelling in base path parameter of 1 st method