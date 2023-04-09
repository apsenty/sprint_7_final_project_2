import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTests extends TestBase {

    @Test
    public void loginCourierPositiveCheck() {
        LoginCourier loginCourier = new LoginCourier("apsenty", "1234");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());
    }

    @Test
    public void loginCourierNonexistentLoginShouldReturnError() {
        LoginCourier loginCourier = new LoginCourier("apsenty123", "1234");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWrongPasswordShouldReturnError() {
        LoginCourier loginCourier = new LoginCourier("apsenty", "12345");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}