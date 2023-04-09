import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class OrderListTests extends TestBase {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void getOrderListWithoutFilters() {

        given().get("/api/v1/orders").then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }

    @Test
    public void getOrderListWithCourierIdFilter() {
        OrderListResponse orderListResponse = given()
                .queryParam("courierId", 175087)
                .get("/api/v1/orders")
                .body()
                .as(OrderListResponse.class);
        List<Orders> actualOrderList = orderListResponse.getOrders();
        assertTrue(actualOrderList.size() > 0);
    }

    @Test
    public void getOrderListWithNearestStationFilter() {
        OrderListResponse orderListResponse = given()
                .queryParam("nearestStation", "[\"96\"]")
                .get("/api/v1/orders")
                .body()
                .as(OrderListResponse.class);
        String orderListResponseToJson = gson.toJson(orderListResponse);
        assertTrue(orderListResponseToJson.contains("\"number\": \"96\""));
    }

    @Test
    public void getOrderListWithLimitFilter() {
        OrderListResponse orderListResponse = given()
                .queryParam("limit", 2)
                .get("/api/v1/orders")
                .body()
                .as(OrderListResponse.class);
        String orderListResponseToJson = gson.toJson(orderListResponse);
        assertTrue(orderListResponseToJson.contains("\"limit\": 2"));
    }

    @Test
    public void getOrderListWithPageFilter() {
        OrderListResponse orderListResponse = given()
                .queryParam("page", 1)
                .get("/api/v1/orders")
                .body()
                .as(OrderListResponse.class);
        String orderListResponseToJson = gson.toJson(orderListResponse);
        assertTrue(orderListResponseToJson.contains("\"page\": 1"));
    }

    @After
    public void tearDown() {

    }
}
