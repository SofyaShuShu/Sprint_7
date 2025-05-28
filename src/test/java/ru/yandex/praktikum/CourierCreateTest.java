package ru.yandex.praktikum;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
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
        Response response = CourierUtils.courierCreate(courier);
        //Проверка кода ответа
        response.then().statusCode(SC_CREATED);
        //Проверка тела ответа
        response.then().body("ok", equalTo(true));
    }

    @Test
    @Step("It is impossible to create two identical couriers")
    public void createDuplicateCourier() throws Exception{
     courier = new Courier("Frodo", "1234", "Frodo");
     //Создание первого курьера
        Response response = CourierUtils.courierCreate(courier);
        response.then().statusCode(SC_CREATED);

     //Повторное создание курьера с такими же данными
        Response responseDuplicate = CourierUtils.courierCreate(courier);
        responseDuplicate.then().statusCode(SC_CONFLICT);

    }

    @Test
    @Description("The test fails due to a discrepancy in the expected and actual message: expected message - \"This username is already in use\", actual message - \"This username is already in use. Try another one.")
    @Step("It is impossible to create two couriers with identical logins")
    public void createCouriersWithDuplicateLogins() throws Exception{
        courier = new Courier("Frodo", "1234", "Frodo");
        Courier courierWithIdenticalLogin = new Courier("Frodo", "5555", "Frodo Beggins");
        //Создание первого курьера
        Response response = CourierUtils.courierCreate(courier);
        response.then().statusCode(SC_CREATED);
        //Повторное создание курьера с таким же логином
        Response responseDuplicateLogin = CourierUtils.courierCreate(courierWithIdenticalLogin);
        //Проверка кода ответа
        responseDuplicateLogin.then().statusCode(SC_CONFLICT);
        //Проверка сообщения об ошибке
        String expectedMessage = "Этот логин уже используется";
        responseDuplicateLogin.then().body("message", equalTo(expectedMessage));
    }


    @After
    @Step("Deleting the created courier from the database")
    public void tearDown() {
        int courierId = CourierUtils.getCourierLogin(courier);
        CourierUtils.courierDelete(courierId);
    }
}
