package ru.yandex.praktikum;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {
    private Courier courier;

    @Before
    @Step("Setup base URL")
    public void setUp() {
        Utils.setUp();
    }

    @Test
    @Step("Create new courier with valid date")
        public void createNewCourierWithValidDate() throws Exception {
        courier = new Courier("Frodo", "1234", "Frodo");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
                response.then().statusCode(201);
    }

    @Test
    @Step("Test correct message, when create new courier")
    public void createNewCourierWithValidDateMessage() throws Exception {
        courier = new Courier("Frodo", "1234", "Frodo");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(201);
        response.then().body("ok", equalTo(true));
    }

    @Test
    @Step("It is impossible to create two identical couriers")
    public void createDuplicateCourier() throws Exception{
     courier = new Courier("Frodo", "1234", "Frodo");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(201);
        Response response2 =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response2.then().statusCode(409);

    }

    @Test
    @Description("The test fails due to a discrepancy in the expected and actual message: expected message - \"This username is already in use\", actual message - \"This username is already in use. Try another one.")
    @Step("It is impossible to create two couriers with identical logins")
    public void createCouriersWithDuplicateLogins() throws Exception{
        courier = new Courier("Frodo", "1234", "Frodo");
        Courier courierWithIdenticalLogin = new Courier("Frodo", "5555", "Frodo Beggins");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(201);
        Response response2 =
                given()
                        .header("Content-type", "application/json")
                        .body(courierWithIdenticalLogin)
                        .when()
                        .post("/api/v1/courier");
        response2.then().statusCode(409);

        String expectedMessage = "Этот логин уже используется";
        response2.then().body("message", equalTo(expectedMessage));
    }


    @After
    @Step("Deleting the created courier from the database")
    public void tearDown() {
        int courierId = CourierUtils.CourierLogin(courier);
        CourierUtils.CourierDelete(courierId);
    }


}
