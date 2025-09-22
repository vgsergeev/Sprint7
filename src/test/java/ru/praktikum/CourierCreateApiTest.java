package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.baseapimethods.CourierBaseApiMethods;
import ru.praktikum.pojos.CourierPojo;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class CourierCreateApiTest extends BaseTest {

    private CourierPojo courierPojo;
    private final Faker faker = new Faker();

    @Before
    public void setUp() {
        courierPojo = new CourierPojo();
        courierPojo.setLogin(faker.name().username())
                .setPassword(faker.internet().password())
                .setFirstName(faker.name().firstName());
    }

    @Test
    @DisplayName("Check create courier status code and body")
    @Description("Проверка успешного создания курьера")
    public void createCourierSuccessCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo)
                .statusCode(HTTP_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Check error create duplicant courier")
    @Description("Проверка возврата ошибки при создании дубля курьера с уже имеющимися данными")
    public void createDuplicantCourierErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo);
        CourierBaseApiMethods
                .createCourier(courierPojo)
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Check error create courier without login")
    @Description("Проверка возврата ошибки при создании курьера без логина")
    public void createCourierWithoutLoginErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo.setLogin(null))
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check error create courier without password")
    @Description("Проверка возврата ошибки при создании курьера без пароля")
    public void createCourierWithoutPasswordErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo.setPassword(null))
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without first name, check status code and body")
    @Description("Проверка успешного создания курьера без указания имени")
    public void createCourierWithoutFirstNameSuccessCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo.setFirstName(null))
                .statusCode(HTTP_CREATED)
                .body("ok", is(true));
    }

    @After
    public void tearDown() {
        Integer id = CourierBaseApiMethods.loginCourier(courierPojo)
                .contentType(ContentType.JSON)
                .extract().body().path("id");
        if (id != null) {
            courierPojo.setId(id);
            CourierBaseApiMethods
                    .deleteCourier(courierPojo)
                    .statusCode(HTTP_OK)
                    .body("ok", is(true));
        }
    }
}