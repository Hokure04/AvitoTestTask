package org.example.Api.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.Api.BaseApiTest;
import org.example.Api.ItemApiClient;
import org.example.Dto.ErrorResponse;
import org.example.Dto.ItemResponse;
import org.example.Utils.AllureUtils;
import org.example.Utils.ItemDataProvider;
import org.example.Utils.ResponseUtils;
import org.example.Utils.SellerIdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Получение объвляения по id")
public class GetItemTests extends BaseApiTest {

    private final ItemApiClient itemApiClient = new ItemApiClient();

    @Test
    @Story("Позитивный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение объявления по существующему идентификатору")
    @Description("Создание объявления и последующее получение объявления по его id")
    public void getItemByIdSuccessfully() {
        int sellerId = SellerIdGenerator.generateSellerId();
        String body = ItemDataProvider.validItem(sellerId);
        AllureUtils.attachJson("Create request body", body);

        Response createResponse = itemApiClient.createItem(body);
        AllureUtils.attachJson("Create response body", createResponse.asPrettyString());
        assertEquals(200, createResponse.statusCode());

        String itemId = ResponseUtils.extractItemIdFromStatus(
                createResponse.as(ItemResponse.class).status()
        );

        Response getResponse = itemApiClient.getItemById(itemId);
        AllureUtils.attachJson("Get item response body", getResponse.asPrettyString());

        assertEquals(200, getResponse.statusCode());
        assertFalse(getResponse.asString().isBlank());
        assertTrue(getResponse.asString().contains(itemId));
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение объявления по несуществующему UUID")
    @Description("Проверка ответа API при запросе объявления по несуществующему идентификатору")
    public void getItemByUnknownId(){
        Response response = itemApiClient.getItemById("02f342ff-1bb7-4601-8803-6a7ce7f67c22");
        AllureUtils .attachJson("Get item response body", response.asPrettyString());

        assertEquals(404, response.statusCode());
        assertFalse(response.asString().isBlank());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.result());
        assertNotNull(errorResponse.status());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение объявления по некорректному идентификатору")
    @Description("Проверка ответа API при запросе объявления по id, не соответствующему формату UUID")
    public void getItemByIncorrectId(){
        Response response = itemApiClient.getItemById("1234566788");
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(400, response.statusCode());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertEquals("400", errorResponse.status());
        assertNotNull(errorResponse.result());
        assertNotNull(errorResponse.result().message());
        assertFalse(errorResponse.result().message().isBlank());
    }
}
