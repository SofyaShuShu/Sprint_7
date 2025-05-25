package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderCreateTest {
    private Order order;


    @Before
    @Step("Setup base URL and create new order")
    public void setUp() {
        Utils.setUp();
        order = OrderUtils.getNewOrder(List.of("BLACK"));
    }

    @Test
    @Step("Checking that the response contains the track")
    public void checkingOrderHasTrack() throws Exception {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post("/api/v1/orders")
                        .then()
                        .statusCode(201)
                        .extract().response();


        int track = response.jsonPath().getInt("track");
        assertThat(track, notNullValue());
//Удаляем созданный заказ
        OrderUtils.orderCancel(track);
    }
}
