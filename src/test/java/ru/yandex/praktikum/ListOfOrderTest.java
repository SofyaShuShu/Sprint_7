package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListOfOrderTest {
    private Order order;


    @Before
    @Step("Setup base URL and create new order")
    public void setUp() {
        Utils.setUp();
        order = OrderUtils.getNewOrder(List.of("BLACK"));
    }

    @Test
    @Step("Сhecking that the list of orders is returned in the response body")
    public void listOfOrdersReturnedBodyInTheResponse(){
        Response responseTrackNumber =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post("/api/v1/orders")
                        .then()
                        .statusCode(201)
                        .extract().response();


        int track = responseTrackNumber.jsonPath().getInt("track");

        Response responseOrderList =
                given()
                        .get("/api/v1/orders")
                        .then()
                        .statusCode(200)
                        .extract().response();

        List<?> orders = responseOrderList.jsonPath().getList("orders");
        assertThat("Тело ответа содержит orders", orders, notNullValue());

//Удаляем созданный заказ
        OrderUtils.orderCancel(track);
    }
}
