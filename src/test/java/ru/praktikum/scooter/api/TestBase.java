package ru.praktikum.scooter.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import ru.praktikum.scooter.api.courier.create.CreateCourier;
import ru.praktikum.scooter.api.courier.login.*;

import static io.restassured.RestAssured.given;

public class TestBase {
    private final String login = "apsenty";
    private final String password = "1234";
    private final String firstName = "olga";
    private int courierId;
    private final String URI_DELETE_COURIER = "/api/v1/courier/";

    @Before
    public void setUp() { //предусловие - создание курьера
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";

        CreateCourier createCourier = new CreateCourier(login, password, firstName);
        Response createCourierResponse = createCourier.getCreateCourierResponse(createCourier);
    }

    @After
    public void tearDown() { // постусловие - удаление курьера
        // шаг 1 - авторизация курьера, кладем id в переменную
        LoginCourier loginCourier = new LoginCourier(login, password);
        LoginResponse loginResponse = loginCourier.getLoginCourierResponse(loginCourier)
                .body()
                .as(LoginResponse.class);
        courierId = loginResponse.getId(); //положили id курьера в переменную

        // шаг 2 - метод delete, в котором передаем переменную с id
        String pathWithCourierId = String.format(URI_DELETE_COURIER.toString()+"%d",courierId);
        given().delete(pathWithCourierId);
    }
}