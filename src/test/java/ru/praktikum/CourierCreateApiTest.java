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
        courierPojo.withLogin(faker.name().username())
                .withPassword(faker.internet().password())
                .withFirstName(faker.name().firstName());
    }

    @Test
    @DisplayName("Check create courier status code and body")
    @Description("Проверка успешного создания курьера")
    public void createCourierSuccessCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo)
                .body("ok", is(true))
                .and()
                .statusCode(HTTP_CREATED);
    }

    @Test
    @DisplayName("Check error create duplicant courier")
    @Description("Проверка возврата ошибки при создании дубля курьера с уже имеющимися данными")
    public void createDuplicantCourierErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo);
        CourierBaseApiMethods
                .createCourier(courierPojo)
                .body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(HTTP_CONFLICT);
    }

    @Test
    @DisplayName("Check error create courier without login")
    @Description("Проверка возврата ошибки при создании курьера без логина")
    public void createCourierWithoutLoginErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo.withLogin(null))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    @DisplayName("Check error create courier without password")
    @Description("Проверка возврата ошибки при создании курьера без пароля")
    public void createCourierWithoutPasswordErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo.withPassword(null))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    @DisplayName("Create courier without first name, check status code and body")
    @Description("Проверка успешного создания курьера без указания имени")
    public void createCourierWithoutFirstNameSuccessCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo.withFirstName(null))
                .body("ok", is(true))
                .and()
                .statusCode(HTTP_CREATED);
    }

    @After
    public void tearDown() {
        Integer id = CourierBaseApiMethods.loginCourier(courierPojo)
                .contentType(ContentType.JSON)
                .extract().body().path("id");
        if (id != null) {
            courierPojo.withId(id);
            CourierBaseApiMethods
                    .deleteCourier(courierPojo)
                    .statusCode(HTTP_OK)
                    .body("ok", is(true));
        }
    }
}