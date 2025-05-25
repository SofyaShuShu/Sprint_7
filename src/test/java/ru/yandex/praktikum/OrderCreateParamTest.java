package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class OrderCreateParamTest {
    private Order order;
    private List<String> color;

    public OrderCreateParamTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    @Before
    @Step("Setup base URL")
    public void setUp() {
        Utils.setUp();
    }

    @Test
    @Step("Parameterized order create test with different scooter color")
    public void orderCreateTestWithDifferentColor() throws Exception {
        order = OrderUtils.getNewOrder(color);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post("/api/v1/orders");
        response.then().statusCode(201);

        int orderTrack = response.jsonPath().getInt("track");
        //Удаляем созданный заказ
        OrderUtils.orderCancel(orderTrack);
    }
}
