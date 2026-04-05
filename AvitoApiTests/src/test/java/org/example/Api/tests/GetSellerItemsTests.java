package org.example.Api.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.Api.BaseApiTest;
import org.example.Api.ItemApiClient;
import org.example.Dto.ErrorResponse;
import org.example.Utils.AllureUtils;
import org.example.Utils.ItemDataProvider;
import org.example.Utils.SellerIdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Получение объявлений по sellerId")
public class GetSellerItemsTests extends BaseApiTest {

    private final ItemApiClient itemApiClient = new ItemApiClient();

    @Test
    @Story("позитивный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение всех объвлений пользователя")
    @Description("Создание нескольких объявлениц м одним и тем же sellerId и получение их списком")
    public void getItemsBySellerIdSuccessfully(){
        int sellerId = SellerIdGenerator.generateSellerId();

        itemApiClient.createItem(ItemDataProvider.validItem(sellerId));
        itemApiClient.createItem(ItemDataProvider.validItem(sellerId));

        Response response = itemApiClient.getItemBySellerId(sellerId);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(200, response.statusCode());
        assertFalse(response.asString().isBlank());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение объявлений пользователя с отрицательным sellerId")
    @Description("Проверка ошибки при запросе объявлений по отрицательному sellerId")
    void getItemsByNegativeSellerId() {
        Response response = itemApiClient.getItemBySellerId(-111111);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(400, response.statusCode());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение объявлений пользователя при sellerId = 0")
    @Description("Проверка ошибки при запросе объявлений по sellerId = 0")
    void getItemsByZeroSellerId() {
        Response response = itemApiClient.getItemBySellerId(0);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(400, response.statusCode());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение объявлений пользователя при sellerId некорректного формата")
    @Description("Проверка ошибки при передаче sellerId в строковом формате")
    void getItemsByInvalidSellerIdFormat() {
        Response response = itemApiClient.getItemBySellerId("adhjsdhjfdsk");
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(400, response.statusCode());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.result());
        assertNotNull(errorResponse.result().message());
        assertFalse(errorResponse.result().message().isBlank());
    }
}
