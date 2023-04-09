import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class DeleteCourierTests extends TestBase {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";

        CreateCourier createCourier = new CreateCourier("apsenty", "1234", "olga");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createCourier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(201);

        LoginCourier loginCourier = new LoginCourier("apsenty", "1234");
        LoginResponse loginResponse = given() //запрос авторизации и десериализация ответа
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post("/api/v1/courier/login")
                .body()
                .as(LoginResponse.class);
        courierId = loginResponse.getId(); //присвоили переменной значение id
    }

    @Test
    public void deleteCourierPositiveCheck() {
        String pathWithCourierId = String.format("/api/v1/courier/%d",courierId);
        Response response =
                given()
                        .delete(pathWithCourierId);
        response.then().statusCode(200)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    public void deleteCourierWithoutIdShouldReturnError() {
        Response response =
                given()
                        .delete("/api/v1/courier/");
        response.then().statusCode(400) // баг, тест упадет, т.к. возвращается 404 статус - несоответствие требованиям
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для удаления курьера"));
    }

    @Test
    public void deleteCourierWrongIdShouldError() {
        Response response =
                given()
                        .delete("/api/v1/courier/000");
        response.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Курьера с таким id нет")); // баг, тест упадет, несоответствие требованиям
    }
}
