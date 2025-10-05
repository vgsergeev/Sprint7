package ru.praktikum.baseapimethods;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.pojos.CourierPojo;

import static ru.praktikum.configconstants.ConfigConstants.*;

public class CourierBaseApiMethods {

    @Step("Create courier API method")
    public static ValidatableResponse createCourier(CourierPojo courierPojo) {
        return RestAssured.given()
                .body(courierPojo)
                .when()
                .post(CREATE_COURIER_ENDPOINT)
                .then();
    }

    @Step("Login courier API method")
    public static ValidatableResponse loginCourier(CourierPojo courierPojo) {
        return RestAssured.given()
                .body(courierPojo)
                .when()
                .post(LOGIN_COURIER_ENDPOINT)
                .then();
    }

    @Step("Delete courier API method")
    public static ValidatableResponse deleteCourier(CourierPojo courierPojo) {
        return RestAssured.given()
                .pathParams("id", courierPojo.getId())
                .when()
                .delete(DELETE_COURIER_ENDPOINT)
                .then();
    }
}