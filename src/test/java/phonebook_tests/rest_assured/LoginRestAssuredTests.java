package phonebook_tests.rest_assured;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import phonebook.dto.AuthRequestDto;
import phonebook.dto.AuthResponseDto;
import phonebook.dto.ErrorDto;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginRestAssuredTests extends TestBase {
    SoftAssert softAssert = new SoftAssert();

    AuthRequestDto body = AuthRequestDto.builder()
            .username("test_qa_1@gmail.com")
            .password("Password@1")
            .build();

    AuthRequestDto errorBody = AuthRequestDto.builder()
            .username("test_qa_1@gmail.com")
            .password("Password@")
            .build();

    @Test
    public void loginSuccessTest() {
        AuthResponseDto dto = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(loginDto)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response().as(AuthResponseDto.class);
        System.out.println(dto);
    }

    @Test
    public void loginSuccessTest2() {
        String responseToken = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(loginDto)
                .then()
                .assertThat()
                .statusCode(200)
                .body(containsString("token"))
                .extract().path("token");
        System.out.println(responseToken);
    }

    @Test
    public void loginWrongPasswordTest() {
        ErrorDto errorDto = given()
                .contentType(ContentType.JSON)
                .body(errorBody)
                .when()
                .post(loginDto)
                .then()
                .assertThat()
                .statusCode(401)
                .extract().response().as(ErrorDto.class);
        System.out.println(errorDto);


        String error = errorDto.getError();
        System.out.println(error);
        softAssert.assertEquals(error, "Unauthorized");

        int status = errorDto.getStatus();
        System.out.println(status);
        softAssert.assertEquals(status, 401);

        String message = (String) errorDto.getMessage();
        System.out.println(message);
        softAssert.assertEquals(message, "Login or Password incorrect");

        String path = errorDto.getPath();
        System.out.println(path);
        softAssert.assertEquals(path, "/v1/user/login/usernamepassword");

        softAssert.assertAll();
    }

    @Test
    public void loginWrongPasswordTest2() {
        given()
                .contentType(ContentType.JSON)
                .body(errorBody)
                .when()
                .post(loginDto)
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("error", equalTo("Unauthorized"))
                .assertThat().body("message", equalTo("Login or Password incorrect"))
                .assertThat().body("status", equalTo(401))
                .assertThat().body("path", equalTo("/v1/user/login/usernamepassword"))
        ;

    }
}
