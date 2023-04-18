package ru.praktikum.scooter.api.order.list;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.scooter.api.TestBase;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class OrderListTests extends TestBase {
    private final String URI_ORDER_LIST = "/api/v1/orders";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Запрос списка заказов без фильтра")
    public void getOrderListWithoutFilters() {

        given().get(URI_ORDER_LIST).then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }

    @Test
    @DisplayName("Запрос списка заказов с фильтром по id курьера")
    public void getOrderListWithCourierIdFilter() {
        OrderListResponse orderListResponse = given()
                .queryParam("courierId", 175087)
                .get(URI_ORDER_LIST)
                .body()
                .as(OrderListResponse.class);
        List<Orders> actualOrderList = orderListResponse.getOrders();
        assertTrue(actualOrderList.size() > 0);
    }

    @Test
    @DisplayName("Запрос списка заказов с фильтром по станции метро")
    public void getOrderListWithNearestStationFilter() {
        OrderListResponse orderListResponse = given()
                .queryParam("nearestStation", "[\"96\"]")
                .get(URI_ORDER_LIST)
                .body()
                .as(OrderListResponse.class);
        assertTrue(orderListResponse.getOrderListResponseToJson(orderListResponse)
                .contains("\"number\": \"96\""));
    }

    @Test
    @DisplayName("Запрос списка заказов с лимитом заказов на странице")
    public void getOrderListWithLimitFilter() {
        OrderListResponse orderListResponse = given()
                .queryParam("limit", 2)
                .get(URI_ORDER_LIST)
                .body()
                .as(OrderListResponse.class);
        assertTrue(orderListResponse.getOrderListResponseToJson(orderListResponse)
                .contains("\"limit\": 2"));
    }

    @Test
    @DisplayName("Запрос списка заказов, проверка пагинации")
    public void getOrderListWithPageFilter() {
        OrderListResponse orderListResponse = given()
                .queryParam("page", 1)
                .get(URI_ORDER_LIST)
                .body()
                .as(OrderListResponse.class);
        assertTrue(orderListResponse.getOrderListResponseToJson(orderListResponse)
                .contains("\"page\": 1"));
    }

    @After
    public void tearDown() {

    }
}
