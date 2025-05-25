package ru.yandex.praktikum;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierUtils {
    public static int CourierLogin(Courier courier) {
        int courierId = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        return courierId;
    }

    public static void CourierDelete(int courierId){
        given()
                .delete("/api/v1/courier/"+courierId)
                .then()
                .statusCode(200);
    }

    public static void courierCreate(Courier courier){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
    }
}
