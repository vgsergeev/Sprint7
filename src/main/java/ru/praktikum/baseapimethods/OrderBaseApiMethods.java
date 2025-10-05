package ru.praktikum.baseapimethods;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.pojos.OrderPojo;

import java.util.Map;

import static ru.praktikum.configconstants.ConfigConstants.*;

public class OrderBaseApiMethods {

    @Step("Create order API method")
    public static ValidatableResponse createOrder(OrderPojo orderPojo) {
        return RestAssured.given()
                .body(orderPojo)
                .when()
                .post(CREATE_ORDER_ENDPOINT)
                .then();
    }

    @Step("Cancel order API method")
    public static ValidatableResponse cancelOrder(OrderPojo orderPojo) {
        return RestAssured.given()
                .body(Map.of("track", orderPojo.getTrack()))
                .when()
                .put(CANCEL_ORDER_ENDPOINT)
                .then();
    }

    @Step("Get orders list API method")
    public static ValidatableResponse getListOrders() {
        return RestAssured.given()
                .when()
                .get(GET_ORDERS_LIST_ENDPOINT)
                .then();
    }
}