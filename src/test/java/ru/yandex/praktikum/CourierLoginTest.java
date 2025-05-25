package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierLoginTest {
    private Courier courier;

    @Before
    @Step("Setup base URL and create courier with valid date")
    public void setUp() {
        Utils.setUp();
        courier = new Courier("Frodo", "1234", "Frodo");
        CourierUtils.courierCreate(courier);
    }

    @Test
    @Step("Login with courier with valid date")
    public void courierLoginWithValidDate() throws Exception{
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(200);
    }

    @Test
    @Step("Login impossible without login")
    public void courierLoginWithoutLogin() throws Exception{
        String requestBody = "{ \"login\": \"\", \"password\": \"" + courier.getPassword() + "\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(400);
    }

    @Test
    @Step("Login impossible without password")
    public void courierLoginWithoutPassword() throws Exception{
        String requestBody = "{ \"login\": \"" + courier.getLogin() + "\", \"password\": \"\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(400);
    }

    @Test
    @Step("Login impossible without login and password")
    public void courierLoginWithoutLoginAndPassword() throws Exception{
        String requestBody = "{ \"login\": \"\", \"password\": \"\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(400);
    }

    @Test
    @Step("Checking message, when login without login")
    public void checkingMessageCourierLoginWithoutLogin() throws Exception{
        String requestBody = "{ \"login\": \"\", \"password\": \"" + courier.getPassword() + "\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(400);

        String expectedMessage = "Недостаточно данных для входа";
        response.then().body("message", equalTo(expectedMessage));

    }

    @Test
    @Step("Checking message, when login without password")
    public void checkingMessageCourierWithoutPassword() throws Exception{
        String requestBody = "{ \"login\": \"" + courier.getLogin() + "\", \"password\": \"\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(400);

        String expectedMessage = "Недостаточно данных для входа";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("Checking message, when login without login and password")
    public void checkingMessageCourierWithoutLoginAndPassword() throws Exception {
        String requestBody = "{ \"login\": \"\", \"password\": \"\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(400);

        String expectedMessage = "Недостаточно данных для входа";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("Login impossible with incorrect login")
    public void courierLoginWithIncorrectLogin() throws Exception{
        String requestBody = "{ \"login\": \"Ivan\", \"password\": \"" + courier.getPassword() + "\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(404);
    }

    @Test
    @Step("Login impossible with incorrect password")
    public void courierLoginWithIncorrectPassword() throws Exception {
        String requestBody = "{ \"login\": \"" + courier.getLogin() + "\", \"password\": \"0000\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(404);
    }

        @Test
        @Step("Login impossible with incorrect login and password")
        public void courierLoginWithIncorrectLoginAndPassword() throws Exception{
            String requestBody = "{ \"login\": \"Ivan\", \"password\": \"0000\" }";
            Response response =
                    given()
                            .header("Content-type", "application/json")
                            .body(requestBody)
                            .when()
                            .post("/api/v1/courier/login");
            response.then().statusCode(404);
    }

    @Test
    @Step("Checking message, when login  with incorrect login")
    public void checkingMessageCourierLoginWithIncorrectLogin() throws Exception{
        String requestBody = "{ \"login\": \"Ivan\", \"password\": \"" + courier.getPassword() + "\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(404);

        String expectedMessage = "Учетная запись не найдена";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("Checking message, when login  with incorrect password")
    public void checkingMessageCourierWithIncorrectPassword() throws Exception {
        String requestBody = "{ \"login\": \"" + courier.getLogin() + "\", \"password\": \"0000\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(404);

        String expectedMessage = "Учетная запись не найдена";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("Checking message, when login  with incorrect login and password")
    public void checkingMessageCourierWithIncorrectLoginAndPassword() throws Exception{
        String requestBody = "{ \"login\": \"Ivan\", \"password\": \"0000\" }";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/v1/courier/login");
        response.then().statusCode(404);

        String expectedMessage = "Учетная запись не найдена";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("Checking that the response contains the id")
    public void authorizationReturnedIdInResponse() throws Exception{
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login")
                        .then()
                        .statusCode(200)
                        .extract().response();

        int id = response.jsonPath().getInt("id");
        assertThat(id, notNullValue());
    }


    @After
    @Step("Deleting the created courier from the database")
    public void tearDown() {
        int courierId = CourierUtils.CourierLogin(courier);
        CourierUtils.CourierDelete(courierId);
    }
    }
