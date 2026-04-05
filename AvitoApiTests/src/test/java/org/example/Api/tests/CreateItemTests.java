package org.example.Api.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.Api.BaseApiTest;
import org.example.Api.ItemApiClient;
import org.example.Dto.ItemResponse;
import org.example.Utils.AllureUtils;
import org.example.Utils.ItemDataProvider;
import org.example.Utils.SellerIdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Создание объвления")
public class CreateItemTests extends BaseApiTest {

    private final ItemApiClient itemApiClient = new ItemApiClient();

    @Test
    @Story("Позитивный сценарйи")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Созадние объявления с корректными данными")
    @Description("Проверка успешного создания объявления с валидным телом запроса")
    public void createItemSuccessfully() {
        int sellerId = SellerIdGenerator.generateSellerId();

        String body = ItemDataProvider.validItem(sellerId);
        AllureUtils.attachJson("Request body", body);

        Response response = itemApiClient.createItem(body);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(200, response.statusCode());

        ItemResponse itemResponse = response.as(ItemResponse.class);
        assertNotNull(itemResponse.status());
        assertFalse(itemResponse.status().isBlank());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание объявления без тела запроса")
    @Description("Проверка ответа API при отправке POST-запроса без body")
    public void createItemWithoutBody(){
        Response response = itemApiClient.createItem("");
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(400, response.statusCode());
        assertEquals(response.asString());
    }
}