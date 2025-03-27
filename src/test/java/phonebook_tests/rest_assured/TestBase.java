package phonebook_tests.rest_assured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {
    public final String loginDto = "/user/login/usernamepassword";
    public final String contactDto = "/contacts";
    public final String AUTH = "Authorization";
    public final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGVzdF9xYV8xQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzQxODA4NDk5LCJpYXQiOjE3NDEyMDg0OTl9.jRtwiqmi77Ex6B5w7WqlqUOA8OarbzdRqho7EfXDUNU";

    @BeforeMethod
    public void init() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

    }
}
