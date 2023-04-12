package ru.praktikum.scooter.api.courier.login;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.praktikum.scooter.api.TestBase;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTests extends TestBase {

    @Step("Отправка запроса авторизации курьера")
    public Response sendRequestLoginCourier(String login, String password) {
        LoginCourier loginCourier = new LoginCourier(login, password);
        return loginCourier.getLoginCourierResponse(loginCourier);
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")
    public void loginCourierPositiveCheck() {
        Response loginCourierResponse = sendRequestLoginCourier("apsenty", "1234");
        loginCourierResponse.then().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация несуществующего пользователя")
    public void loginCourierNonexistentLoginShouldReturnError() {
        Response loginCourierResponse = sendRequestLoginCourier("apsenty123", "1234");
        loginCourierResponse.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неправильным паролем")
    public void loginCourierWrongPasswordShouldReturnError() {
        Response loginCourierResponse = sendRequestLoginCourier("apsenty", "123456");
        loginCourierResponse.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}