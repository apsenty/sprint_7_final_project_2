import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class LoginCourierParameterizedTests extends TestBase {
    private final String login;
    private final String password;

    public LoginCourierParameterizedTests(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] setData() {
        return new Object[][] {
                {"apsenty", null}, //баг, тест упадет, т.к. возвращается 504 статус (проверено в postman)
                {null, "1234"},
        };
    }

    @Test
    public void loginCourierLoginWithoutRequiredFieldShouldReturnError() {
        LoginCourier loginCourier = new LoginCourier(login, password);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}