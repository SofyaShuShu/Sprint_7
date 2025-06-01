package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
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
        Response response = CourierUtils.courierCreate(courier);
        //Проверка кода ответа
        response.then().statusCode(SC_BAD_REQUEST);
        //Проверка сообщения об ошибке
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("It is impossible to create new courier without password")
    public void createCourierWithoutPassword() throws Exception{
        courier = new Courier("Frodo", null, "Frodo");
        Response response = CourierUtils.courierCreate(courier);
        //Проверка кода ответа
        response.then().statusCode(SC_BAD_REQUEST);
        //Проверка сообщения об ошибке
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Description("The test fails, which means that the courier can be created without specifying the firstname.")
    @Step("It is impossible to create new courier without firstname")
    public void createCourierWithoutFirstname() throws Exception{
        courier = new Courier("Frod", "1234", null);
        Response response = CourierUtils.courierCreate(courier);
        //Проверка кода ответа
        response.then().statusCode(SC_BAD_REQUEST);
        //Проверка сообщения об ошибке
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        response.then().body("message", equalTo(expectedMessage));
    }
}

