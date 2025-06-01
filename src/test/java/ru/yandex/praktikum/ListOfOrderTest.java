package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListOfOrderTest {
    private Order order;
    private int track;


    @Before
    @Step("Setup base URL and create new order")
    public void setUp() {
        Utils.setUp();
        order = OrderUtils.getNewOrder(List.of("BLACK"));
        Response responseTrackNumber = OrderUtils.createOrder(order);
        track = responseTrackNumber.jsonPath().getInt("track");
    }

    @Test
    @Step("Сhecking that the list of orders is returned in the response body")
    public void listOfOrdersReturnedBodyInTheResponse(){
        Response responseOrderList = OrderUtils.getOrderList();
        List<?> orders = responseOrderList.jsonPath().getList("orders");
        assertThat("Тело ответа содержит orders", orders, notNullValue());


    }
    @After
    @Step("Deleting the created order from the database")
    public void tearDown() {
        OrderUtils.orderCancel(track);
    }
}

