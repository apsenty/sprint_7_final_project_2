package ru.praktikum.scooter.api.courier.delete;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import ru.praktikum.scooter.api.TestBase;
import ru.praktikum.scooter.api.courier.create.CreateCourier;
import ru.praktikum.scooter.api.courier.login.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class DeleteCourierTests extends TestBase {
    int courierId;
    final String URI_DELETE_COURIER = "/api/v1/courier/";
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";

        CreateCourier createCourier = new CreateCourier("apsenty", "1234", "olga");
        Response createCourierResponse = createCourier.getCreateCourierResponse(createCourier);
        createCourierResponse.then().statusCode(201);

        LoginCourier loginCourier = new LoginCourier("apsenty", "1234");
        LoginResponse loginResponse = loginCourier.getLoginCourierResponse(loginCourier)
                .body()
                .as(LoginResponse.class);
        courierId = loginResponse.getId(); //присвоили переменной значение id
    }

    @Test
    @DisplayName("Успешное удаление курьера")
    public void deleteCourierPositiveCheck() {
        String pathWithCourierId = String.format(URI_DELETE_COURIER.toString()+"%d",courierId);
        Response response =
                given()
                        .delete(pathWithCourierId);
        response.then().statusCode(200)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Удаление курьера без передачи id")
    public void deleteCourierWithoutIdShouldReturnError() {
        Response response =
                given()
                        .delete(URI_DELETE_COURIER);
        response.then().statusCode(400) // баг, тест упадет, т.к. возвращается 404 статус - несоответствие требованиям
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для удаления курьера"));
    }

    @Test
    @DisplayName("Удаление курьера, неправильный id")
    public void deleteCourierWrongIdShouldError() {
        Response response =
                given()
                        .delete(URI_DELETE_COURIER+"000");
        response.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Курьера с таким id нет")); // баг, тест упадет, несоответствие требованиям
    }
}
