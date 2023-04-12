package ru.praktikum.scooter.api.courier.create;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourier {
    private String login;
    private String password;
    private String firstName;
    final String URI_CREATE_COURIER = "/api/v1/courier";


    public CreateCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public CreateCourier() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Response getCreateCourierResponse(Object bodyCreateCourier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(bodyCreateCourier)
                .when()
                .post(URI_CREATE_COURIER);
    }
}