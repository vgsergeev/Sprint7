package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import net.datafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.baseapimethods.OrderBaseApiMethods;
import ru.praktikum.pojos.OrderPojo;

import java.util.concurrent.TimeUnit;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(Parameterized.class)
public class OrderCreateApiTest extends BaseTest {

    private OrderPojo orderPojo;
    private ValidatableResponse response;
    private final String[] color;
    private final Faker faker = new Faker();

    public OrderCreateApiTest(String[] color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        orderPojo = new OrderPojo();
        orderPojo.setFirstName(faker.name().firstName())
                .setLastName(faker.name().lastName())
                .setAddress(faker.address().fullAddress())
                .setMetroStation(faker.number().numberBetween(1,20))
                .setPhone(String.valueOf(faker.phoneNumber().phoneNumber()))
                .setRentTime(faker.number().numberBetween(1,30))
                .setDeliveryDate(String.valueOf(faker.date().future(30, TimeUnit.DAYS, "yyyy-MM-dd")))
                .setComment(faker.lorem().paragraph());
    }

    @Parameterized.Parameters(name = "Тест {index} должен возвращать ответ об успешном заказе с треком")
    public static Object[][] getColorOrder(){
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Test
    @DisplayName("Check order create status code and body")
    @Description("Проверка успешного создания заказа")
    public void createOrderColorSuccessCheck(){
        orderPojo.setColor(color);
        response = OrderBaseApiMethods
                .createOrder(orderPojo);
        response
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue());
    }

    @After
    public void tearDown() {
        Integer track = response.extract().body().path("track");
        orderPojo.setTrack(track);
        OrderBaseApiMethods
                .cancelOrder(orderPojo)
                .statusCode(HTTP_OK)
                .body("ok", is(true));
    }
}