package ru.praktikum.scooter.api.courier.create;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import ru.praktikum.scooter.api.TestBase;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTests extends TestBase {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Step("Отправка запроса на создание курьера")
    public Response sendRequestCreateCourier(String login, String password, String firstName) {
        CreateCourier createCourier = new CreateCourier(login, password, firstName);
        return createCourier.getCreateCourierResponse(createCourier);
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierPositiveCheck() {
        Response createCourierResponse = sendRequestCreateCourier("apsenty",
                "1234", "olga");
        createCourierResponse.then().statusCode(201);
    }

    @Test
    @DisplayName("Ошибка при создании двух одинаковых курьеров")
    public void createCourierCreateTwoEqualCouriersShouldReturnError() {
        //шаг 1 - создание первого курьера
        Response createCourierResponse = sendRequestCreateCourier("apsenty",
                "1234", "olga");
        createCourierResponse.then().statusCode(201);
        // шаг 2 - создание такого же курьера
        Response createSecondCourierResponse = sendRequestCreateCourier("apsenty",
                "1234", "olga");
        createSecondCourierResponse.then().statusCode(409);
    }

    @Test
    @DisplayName("Ошибка при создании курьера без обязательных полей")
    public void createCourierBodyWithoutRequireFieldShouldReturnError() {
        Response createCourierResponse = sendRequestCreateCourier("apsenty",null,null);
        createCourierResponse.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Ошибка при создании курьера с уже существующим логином")
    public void createCourierEqualLoginShouldReturnError() {
        //шаг 1 - создание первого курьера
        Response createCourierResponse = sendRequestCreateCourier("apsenty",
                "1234", "olga");
        createCourierResponse.then().statusCode(201);
        //шаг 2 - создание курьера с таким же логином
        Response createSameCourierResponse = sendRequestCreateCourier("apsenty", "1111", "john");
        createSameCourierResponse.then().statusCode(409);
    }
}