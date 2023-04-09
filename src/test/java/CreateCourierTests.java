import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTests extends TestBase {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createCourierPositiveCheck() {
        CreateCourier createCourier = new CreateCourier("apsenty", "1234", "olga");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createCourier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(201) //проверка статуса
                .and()
                .assertThat().body("ok", equalTo(true)); //проверка ответа
    }

    @Test
    public void createCourierCreateTwoEqualCouriersShouldReturnError() {
        //шаг 1 - создание первого курьера
        CreateCourier createCourier = new CreateCourier("apsenty", "1234", "olga");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201); //проверка статуса
        // шаг 2 - создание такого же курьера
        given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(409);
    }

    @Test
    public void createCourierBodyWithoutRequireFieldShouldReturnError() {
        CreateCourier createCourier = new CreateCourier("apsenty",null,null);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(createCourier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierEqualLoginShouldReturnError() {
        CreateCourier createCourier = new CreateCourier("apsenty", "1234", "olga");
        CreateCourier sameLoginCourier = new CreateCourier("apsenty", "1111", "john");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201); //проверка статуса
        // шаг 2 - создание курьера с таким же логином
        given()
                .header("Content-type", "application/json")
                .and()
                .body(sameLoginCourier)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(409);
    }

}