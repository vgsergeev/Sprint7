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
import static org.hamcrest.CoreMatchers.*;

public class CourierLoginApiTest extends BaseTest {

    private CourierPojo courierPojo;
    private CourierPojo errorCourierPojo;
    private final Faker faker = new Faker();

    @Before
    public void setUp() {
        courierPojo = new CourierPojo();
        errorCourierPojo = new CourierPojo();
        courierPojo.withLogin(faker.name().username())
                .withPassword(faker.internet().password());
    }

    @Test
    @DisplayName("Check login courier status code and body")
    @Description("Проверка успешной авторизации курьера")
    public void loginCourierSuccessCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo);
        CourierBaseApiMethods
                .loginCourier(courierPojo)
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Check error login non-existing courier")
    @Description("Проверка возврата ошибки при авторизации несуществующего курьера")
    public void loginCourierNonExistingUserErrorCheck() {
        CourierBaseApiMethods
                .loginCourier(courierPojo)
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check error login courier without login data")
    @Description("Проверка возврата ошибки при авторизации курьера в отсутствие логина")
    public void loginCourierWithoutLoginErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo);
        CourierBaseApiMethods
                .loginCourier(courierPojo.withLogin(null))
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check error login courier without password data")
    @Description("Проверка возврата ошибки при авторизации курьера в отсутствие пароля")
    public void loginCourierWithoutPasswordErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo);
        CourierBaseApiMethods
                .loginCourier(courierPojo.withPassword(null))
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Check error login courier with wrong login")
    @Description("Проверка возврата ошибки при авторизации курьера с неверным логином")
    public void loginCourierWithWrongLoginErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo);
        errorCourierPojo = courierPojo.withLogin(faker.name().username());
        CourierBaseApiMethods
                .loginCourier(errorCourierPojo)
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Check error login courier with wrong password")
    @Description("Проверка возврата ошибки при авторизации курьера с неверным паролем")
    public void loginCourierWithWrongPasswordErrorCheck() {
        CourierBaseApiMethods
                .createCourier(courierPojo);
        errorCourierPojo = courierPojo.withPassword(faker.internet().password());
        CourierBaseApiMethods
                .loginCourier(errorCourierPojo)
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
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