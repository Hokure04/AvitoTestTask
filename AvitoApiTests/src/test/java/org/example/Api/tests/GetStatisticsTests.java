package org.example.Api.tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
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

@Feature("Получение статистики для объявления")
public class GetStatisticsTests  extends BaseApiTest {

    private final ItemApiClient itemApiClient = new ItemApiClient();

    @Test
    @Story("Позитивный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение статистики по существующему идентификатору объявления")
    @Description("Создание объявления и получение статистики по его id")
    public void getStatisticsByItemId(){
        int sellerId = SellerIdGenerator.generateSellerId();
        String body = ItemDataProvider.validItem(sellerId);
        AllureUtils.attachJson("Request body", body);

        Response creationResponse = itemApiClient.createItem(body);
        AllureUtils.attachJson("Creation response:", creationResponse.getBody().asString());
        assertEquals(200, creationResponse.getStatusCode());

        String itemId = ResponseUtils.extractItemIdFromStatus(creationResponse.as(ItemResponse.class).status());
        Response statisticsResponse = itemApiClient.getStatisticsById(itemId);
        AllureUtils.attachJson("Response body", statisticsResponse.getBody().asString());

        assertEquals(200, statisticsResponse.getStatusCode());
        assertFalse(statisticsResponse.asString().isBlank());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение статистики по несуществующему идентификатору объявления")
    @Description("Проверка ответа API при запросе статистики по несуществующему id")
    public void getStatisticsByUnknownId(){
        Response response = itemApiClient.getStatisticsById("02f342ff-1bb7-4601-8803-6a7ce7f67c22");
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(404, response.getStatusCode());
        assertFalse(response.asString().isBlank());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.status());
        assertNotNull(errorResponse.result());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение статистики по идентификатору некорректного формата")
    @Description("Проверка ответа API при запросе статистики по невалидному id")
    public void getStatisticByInvalidId(){
        Response response = itemApiClient.getItemBySellerId("12325245666");
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(404, response.getStatusCode());
        assertFalse(response.asString().isBlank());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.result());
        assertNotNull(errorResponse.result().message());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Получение статистики для удалённого объявления")
    @Description("Создание объявления, его удаление и последующая попытка получить статистику")
    void getStatisticsForDeletedItem() {
        int sellerId = SellerIdGenerator.generateSellerId();
        String body = ItemDataProvider.validItem(sellerId);
        AllureUtils.attachJson("Create request body", body);

        Response createResponse = itemApiClient.createItem(body);
        AllureUtils.attachJson("Create response body", createResponse.asPrettyString());
        assertEquals(200, createResponse.statusCode());

        String itemId = ResponseUtils.extractItemIdFromStatus(
                createResponse.as(ItemResponse.class).status()
        );

        Response deleteResponse = itemApiClient.deleteItemById(itemId);
        AllureUtils.attachJson("Delete response body", deleteResponse.asPrettyString());
        assertEquals(200, deleteResponse.statusCode());

        Response statisticResponse = itemApiClient.getStatisticsById(itemId);
        AllureUtils.attachJson("Statistic after delete response body", statisticResponse.asPrettyString());

        assertEquals(404, statisticResponse.statusCode());
    }
}
