package ru.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.praktikum.baseapimethods.OrderBaseApiMethods;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetListApiTest extends BaseTest {

    @Test
    @DisplayName("Get order list, check status code and body")
    @Description("Проверка успешного получения списка заказов")
    public void getOrderListSuccessCheck() {
        OrderBaseApiMethods.getListOrders()
                .body("orders", notNullValue())
                .and()
                .statusCode(HTTP_OK);
    }
}