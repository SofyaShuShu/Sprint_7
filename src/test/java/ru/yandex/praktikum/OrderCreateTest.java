package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderCreateTest {
    private Order order;
    private int track;


    @Before
    @Step("Setup base URL and create new order")
    public void setUp() {
        Utils.setUp();
    }

    @Test
    @Step("Checking that the response contains the track")
    public void checkingOrderHasTrack() throws Exception {
        Order newOrder = OrderUtils.getNewOrder(List.of("BLACK"));
        Response response = OrderUtils.createOrder(newOrder);
        track = response.jsonPath().getInt("track");
        assertThat(track, notNullValue());
    }

    @After
    @Step("Deleting the created order from the database")
    public void tearDown() {
        OrderUtils.orderCancel(track);
    }
}

