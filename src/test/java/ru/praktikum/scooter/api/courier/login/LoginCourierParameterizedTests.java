package ru.praktikum.scooter.api.courier.login;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.scooter.api.TestBase;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginCourierParameterizedTests extends TestBase {
    private final String login;
    private final String password;

    public LoginCourierParameterizedTests(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters(name = "Авторизация курьера без обязательного поля. " +
            "Тестовые данные: {0} {1}")
    public static Object[][] setData() {
        return new Object[][] {
                {"apsenty", null}, //баг, тест упадет, т.к. возвращается 504 статус (проверено в postman)
                {null, "1234"},
        };
    }

    @Test
    @DisplayName("Авторизация курьера, нет обязательного поля")
    public void loginCourierLoginWithoutRequiredFieldShouldReturnError() {
        LoginCourier loginCourier = new LoginCourier(login, password);
        Response loginCourierResponse = loginCourier.getLoginCourierResponse(loginCourier);
        loginCourierResponse.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}