package ru.yandex.praktikum;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderUtils {

    public static void orderCancel(int orderTrack){
        given()
                .header("Content-type", "application/json")
                .body("{\"track\": " + orderTrack + "}")
                .put("/api/v1/orders/cancel")
                .then();
    }

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
}
