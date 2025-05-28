package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static org.apache.http.HttpStatus.*;

import java.util.List;

import static io.restassured.RestAssured.given;


public class OrderUtils {
    @Step("Method for cancel order")
    public static void orderCancel(int orderTrack){
        given()
                .header("Content-type", "application/json")
                .body("{\"track\": " + orderTrack + "}")
                .put("/api/v1/orders/cancel")
                .then();
    }

    @Step("Method for filling in the order data")
    public static Order getNewOrder(List<String> color){
        return new Order(
                "Sofya",
                "SofyaShu",
                "Puschino",
                1,
                "+7 800 355 35 35",
                1,
                "2025-06-04",
                "Comment-comment-comment",
                color);
    }

    @Step("Method for sending an order creation request")
    public static Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .response();
    }

    @Step("Method for getting a list of orders")
    public static Response getOrderList(){
        Response response =
                given()
                        .get("/api/v1/orders")
                        .then()
                        .statusCode(SC_OK)
                        .extract().response();
        return response;
    }

}

