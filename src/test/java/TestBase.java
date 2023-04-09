import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class TestBase {
    int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";

        CreateCourier createCourier = new CreateCourier("apsenty", "1234", "olga");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);
    }

    @After
    public void tearDown() { // постусловие - удаление курьера
        // шаг 1 - авторизация курьера, кладем id в переменную
        LoginCourier loginCourier = new LoginCourier("apsenty", "1234");
        LoginResponse response = given() //запрос авторизации и десериализация ответа
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login")
                .body()
                .as(LoginResponse.class);
        courierId = response.getId(); //положили id курьера в переменную

        // шаг 2 - метод delete, в котором передаем переменную с id
        String pathWithCourierId = String.format("/api/v1/courier/%d",courierId);
        given().delete(pathWithCourierId); //нужно ли в постусловии проверять успешность запроса?
    }
}