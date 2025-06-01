package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static org.apache.http.HttpStatus.*;

import static io.restassured.RestAssured.given;

public class CourierUtils {
    @Step("Method for getting the courier id")
    public static int getCourierLogin(Courier courier) {
        int courierId = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract()
                .path("id");

        return courierId;
    }
    @Step("Method for courier delete")
    public static void courierDelete(int courierId){
        given()
                .delete("/api/v1/courier/"+courierId)
                .then()
                .statusCode(SC_OK);
    }
    @Step("Method for courier create")
    public static Response courierCreate(Courier courier){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        return response;
    }

    @Step("Method for courier login")
    public static Response courierLogin(Courier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

}

