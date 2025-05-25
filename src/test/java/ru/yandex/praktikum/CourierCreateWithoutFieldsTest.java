package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateWithoutFieldsTest {
    private Courier courier;

    @Before
    public void setUp() {
        Utils.setUp();
    }

    @Test
    @Step("It is impossible to create new courier without login")
    public void createCourierWithoutLogin() throws Exception{
        courier = new Courier(null, "1234", "Frodo");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(400);
    }

    @Test
    @Step("It is impossible to create new courier without password")
    public void createCourierWithoutPassword() throws Exception{
        courier = new Courier("Frodo", null, "Frodo");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(400);
    }

    @Test
    @Description("The test fails, which means that the courier can be created without specifying the firstname.")
    @Step("It is impossible to create new courier without firstname")
    public void createCourierWithoutFirstname() throws Exception{
        courier = new Courier("Frod", "1234", null);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");
        response.then().statusCode(400);
    }

    @Test
    @Step("Test correct message, when create new courier without login")
    public void testMessageForCourierCreateWithoutLogin() throws Exception{
        courier = new Courier(null, "1234", "Frodo");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("Test correct message, when create new courier without password")
    public void testMessageForCourierCreateWithoutPassword() throws Exception{
        courier = new Courier("FrodoBeggins", null, "Frodo");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        response.then().body("message", equalTo(expectedMessage));
    }
    @Test
    @Description("The test fails due to a discrepancy in the expected and actual message: expected message - \"Not enough data to create an account\", actual message - \"null")
    @Step("Test correct message, when create new courier without firstname")
    public void testMessageForCourierCreateWithoutFirstname() throws Exception{
        courier = new Courier("FrodoCourier", "1234", null);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when()
                        .post("/api/v1/courier");

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        response.then().body("message", equalTo(expectedMessage));
    }
}
