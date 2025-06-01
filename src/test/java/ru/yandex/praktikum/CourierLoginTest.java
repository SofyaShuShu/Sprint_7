package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
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
        Response response = CourierUtils.courierLogin(courier);
        //Проверка кода ответа
        response.then().statusCode(SC_OK);
    }

    @Test
    @Step("Login impossible without login")
    public void courierLoginWithoutLogin() throws Exception{
        Courier courierWithoutLogin = new Courier("", courier.getPassword(), null);
        Response response = CourierUtils.courierLogin(courierWithoutLogin);
        //Проверка кода ответа
        response.then().statusCode(SC_BAD_REQUEST);
        //Проверка тела ответа
        String expectedMessage = "Недостаточно данных для входа";
        response.then().body("message", equalTo(expectedMessage));

    }

    @Test
    @Step("Login impossible without password")
    public void courierLoginWithoutPassword() throws Exception{
        Courier courierWithoutPassword = new Courier(courier.getLogin(), "", null);
        Response response = CourierUtils.courierLogin(courierWithoutPassword);
        //Проверка кода ответа
        response.then().statusCode(SC_BAD_REQUEST);
        //Проверка тела ответа
        String expectedMessage = "Недостаточно данных для входа";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("Login impossible without login and password")
    public void courierLoginWithoutLoginAndPassword() throws Exception{
        Courier courierWithoutLoginAndPassword = new Courier("", "", null);
        Response response = CourierUtils.courierLogin(courierWithoutLoginAndPassword);
        //Проверка кода ответа
        response.then().statusCode(SC_BAD_REQUEST);
        //Проверка тела ответа
        String expectedMessage = "Недостаточно данных для входа";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("Login impossible with incorrect login")
    public void courierLoginWithIncorrectLogin() throws Exception {
        Courier courierLoginWithIncorrectLogin = new Courier("Ivan", courier.getPassword(), null);
        Response response = CourierUtils.courierLogin(courierLoginWithIncorrectLogin);
        //Проверка кода ответа
        response.then().statusCode(SC_NOT_FOUND);
        //Проверка тела ответа
        String expectedMessage = "Учетная запись не найдена";
        response.then().body("message", equalTo(expectedMessage));
    }

    @Test
    @Step("Login impossible with incorrect password")
    public void courierLoginWithIncorrectPassword() throws Exception {
        Courier courierLoginWithIncorrectLogin = new Courier(courier.getLogin(), "0000", null);
        Response response = CourierUtils.courierLogin(courierLoginWithIncorrectLogin);
        //Проверка кода ответа
        response.then().statusCode(SC_NOT_FOUND);
        //Проверка тела ответа
        String expectedMessage = "Учетная запись не найдена";
        response.then().body("message", equalTo(expectedMessage));
    }

        @Test
        @Step("Login impossible with incorrect login and password")
        public void courierLoginWithIncorrectLoginAndPassword() throws Exception{
            Courier courierLoginWithIncorrectLogin = new Courier("Ivan", "0000", null);
            Response response = CourierUtils.courierLogin(courierLoginWithIncorrectLogin);
            //Проверка кода ответа
            response.then().statusCode(SC_NOT_FOUND);
            //Проверка тела ответа
            String expectedMessage = "Учетная запись не найдена";
            response.then().body("message", equalTo(expectedMessage));
    }


    @Test
    @Step("Checking that the response contains the id")
    public void authorizationReturnedIdInResponse() throws Exception{
        Response response = CourierUtils.courierLogin(courier);

        int id = response.jsonPath().getInt("id");
        assertThat(id, notNullValue());
    }


    @After
    @Step("Deleting the created courier from the database")
    public void tearDown() {
        int courierId = CourierUtils.getCourierLogin(courier);
        CourierUtils.courierDelete(courierId);
    }
    }

