package ru.praktikum.scooter.api.courier.login;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginCourier {
    private String login;
    private String password;
    final String URI_LOGIN_COURIER = "/api/v1/courier/login";

    public LoginCourier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginCourier() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Response getLoginCourierResponse(Object bodyLoginCourier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(bodyLoginCourier)
                .when()
                .post(URI_LOGIN_COURIER);
    }
}