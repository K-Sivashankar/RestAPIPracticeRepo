package InterviewQandA.complexjson;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import InterviewQandA.complexjson.Role;


import static io.restassured.RestAssured.*;

public class ValidateComplexUserTest {

    @Test
    public void validateComplexJsonWithPojo() {
        RestAssured.baseURI = "https://api.example.com"; // Replace with actual base URL

        Response response = given()
                .header("Authorization", "Bearer your_token")
                .when()
                .get("/users/101")
                .then()
                .statusCode(200)
                .extract().response();

        UserResponse user = response.as(UserResponse.class);

        SoftAssert sa = new SoftAssert();

        // Validate basic fields
        sa.assertEquals(user.getId(), 101, "ID mismatch");
        sa.assertEquals(user.getName(), "Siva", "Name mismatch");
        sa.assertEquals(user.getEmail(), "siva@example.com", "Email mismatch");
        sa.assertEquals(user.getStatus(), "active", "Status mismatch");

        // Validate nested object (Address)
        Address address = user.getAddress();
        sa.assertEquals(address.getStreet(), "123 Tech Lane", "Street mismatch");
        sa.assertEquals(address.getCity(), "Chennai", "City mismatch");
        sa.assertEquals(address.getZip(), "600001", "ZIP mismatch");

        // Validate array of roles
        sa.assertEquals(user.getRoles().size(), 2, "Role count mismatch");

        // Validate first role
        Role adminRole = user.getRoles().get(0);
        sa.assertEquals(adminRole.getName(), "admin", "First role name mismatch");
        sa.assertTrue(adminRole.getPermissions().contains("delete"), "Missing permission in admin");

        // Validate second role
        Role userRole = user.getRoles().get(1);
        sa.assertEquals(userRole.getName(), "user", "Second role name mismatch");
        sa.assertEquals(userRole.getPermissions().size(), 1, "User role permissions size mismatch");
        sa.assertEquals(userRole.getPermissions().get(0), "read", "User role permission mismatch");

        sa.assertAll();
    }
}

