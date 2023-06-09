package ru.praktikum.scooter.api.order.create;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.scooter.api.TestBase;
import java.util.List;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)
public class CreateOrderTest extends TestBase {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation,
                           String phone, int rentTime, String deliveryDate,
                           String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Создание заказа. Тестовые данные: {0}, {1}, {2}, {3}")
    public static Object[][] setData() {
        return new Object[][] {
                {"Ольга", "Полякова", "Ярославская ул., 9", "ВДНХ", "+79001234567", 2, "2023-04-01",
                        "comment", List.of("BLACK")},
                {"Ольга", "Полякова", "Ярославская ул., 9", "ВДНХ", "+79001234567", 2, "2023-04-01",
                        "comment", List.of("GRAY")},
                {"Ольга", "Полякова", "Ярославская ул., 9", "ВДНХ", "+79001234567", 2, "2023-04-01",
                        "comment", List.of("GRAY", "BLACK")},
                {"Ольга", "Полякова", "Ярославская ул., 9", "ВДНХ", "+79001234567", 2, "2023-04-01",
                        "comment", null},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Успешное создание заказа")
    public void CreateOrderPositiveCheck() {
        CreateOrder createOrder = new CreateOrder(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, color);
        Response createOrderResponse = createOrder.getCreateOrderResponse(createOrder);
        createOrderResponse.then().statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());
    }

    @After
    public void tearDown() {
    }
}
