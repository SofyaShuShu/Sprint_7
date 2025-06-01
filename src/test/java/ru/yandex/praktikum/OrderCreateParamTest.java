package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class OrderCreateParamTest {
    private Order order;
    private int track;
    private List<String> color;

    public OrderCreateParamTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    @Before
    @Step("Setup base URL")
    public void setUp() {
        Utils.setUp();
    }

    @Test
    @Step("Parameterized order create test with different scooter color")
    public void orderCreateTestWithDifferentColor() throws Exception {
        order = OrderUtils.getNewOrder(color);
        Response response = OrderUtils.createOrder(order);
        assertThat("Статус кода ответа на создание заказа", response.statusCode(), equalTo(SC_CREATED));
        track = response.jsonPath().getInt("track");
    }

    @After
    @Step("Deleting the created order from the database")
    public void tearDown() {
        OrderUtils.orderCancel(track);
    }
}
