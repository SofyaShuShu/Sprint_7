package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Utils {
    public static void setUp(){
      RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

}
