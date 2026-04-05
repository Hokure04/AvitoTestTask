package org.example.Api.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.example.Api.BaseApiTest;
import org.example.Api.ItemApiClient;
import org.example.Dto.ErrorResponse;
import org.example.Dto.ItemDTO;
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

        ItemDTO itemResponse = response.as(ItemDTO.class);

        assertNotNull(itemResponse);
        assertFalse(itemResponse.id().isBlank());

        assertEquals(sellerId, itemResponse.sellerId());
        assertNotNull(itemResponse.name());
        assertFalse(itemResponse.name().isBlank());

        assertNotNull(itemResponse.price());
        assertNotNull(itemResponse.statistics());
        assertNotNull(itemResponse.createdAt());
        assertFalse(itemResponse.createdAt().isBlank());
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
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.result());
        assertNotNull(errorResponse.result().message());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание объявления без sellerId")
    @Description("Проверка ошибки при отсутствии обязательного поля sellerId")
    public void createItemWithSellerId(){
        String body = ItemDataProvider.missingSellerId();
        AllureUtils.attachJson("Request body", body);

        Response response = itemApiClient.createItem(body);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(400, response.statusCode());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.result());
        assertNotNull(errorResponse.result().message());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание объявления с некорректным типом поля price")
    @Description("Проверка ошибки при передаче price как числа с плавающей точкой")
    public void createItemWithInvalidPriceValue(){
        int sellerId = SellerIdGenerator.generateSellerId();
        String body = ItemDataProvider.invalidPriceType(sellerId);
        AllureUtils.attachJson("Request body", body);

        Response response = itemApiClient.createItem(body);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(400, response.statusCode());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.result());
        assertNotNull(errorResponse.result().message());

    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание объявления с отрицательным значением поля price")
    @Description("Проверка ошибки при передаче отрцательного значения price")
    public void createItemWithNegativePrice(){
        int sellerId = SellerIdGenerator.generateSellerId();
        String body = ItemDataProvider.negativePrice(sellerId);
        AllureUtils.attachJson("Request body", body);

        Response response = itemApiClient.createItem(body);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(400, response.statusCode());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.result());
        assertNotNull(errorResponse.result().message());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание объявления со значением price выше диапазона integer")
    @Description("Проверка ошибки при передаче price больше максимального значения Integer")
    public void createItemWithOverflowIntegerValue(){
        int sellerId = SellerIdGenerator.generateSellerId();
        String body = ItemDataProvider.overflowInteger(sellerId);
        AllureUtils.attachJson("Request body", body);

        Response response = itemApiClient.createItem(body);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(400, response.statusCode());
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.result());
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание объявления со значением price выше диапазона long")
    @Description("Проверка ошибки при передаче price больше максимального значения Long")
    public void createItemWithOverflowLongValue(){
        int sellerId = SellerIdGenerator.generateSellerId();
        String body = ItemDataProvider.overflowLong(sellerId);
        AllureUtils.attachJson("Request body", body);

        Response response = itemApiClient.createItem(body);
        AllureUtils.attachJson("Response body", response.asPrettyString());
        assertEquals(400, response.statusCode());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertNotNull(errorResponse.result());
    }

    @Test
    @Story("Позитивный сценарий")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Создание объявления со значением price равным максимальному значению типа Long")
    @Description("Проверка успешного создания объявления при значение price равному максимальному значению Long")
    public void createItemWithMaxLongValue() {
        int sellerId = SellerIdGenerator.generateSellerId();
        String body = ItemDataProvider.maxLong(sellerId);
        AllureUtils.attachJson("Request body", body);

        Response response = itemApiClient.createItem(body);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(200, response.statusCode());

        ItemDTO item = response.as(ItemDTO.class);

        assertNotNull(item.id());
        assertFalse(item.id().isBlank());

        assertEquals(sellerId, item.sellerId());
        assertEquals(Long.MAX_VALUE, Long.valueOf(item.price()));

        assertNotNull(item.name());
        assertFalse(item.name().isBlank());

        assertNotNull(item.statistics());
        assertNotNull(item.createdAt());
        assertFalse(item.createdAt().isBlank());
    }

    @Test
    @Story("Позитивный сценарий")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Создание объявления со слишком длинным name")
    @Description("Проверка создания объявления с очень длинным значением поля name")
    public void createItemWithLongName() {
        int sellerId = SellerIdGenerator.generateSellerId();
        String body = ItemDataProvider.longName(sellerId);
        AllureUtils.attachJson("Request body", body);

        Response response = itemApiClient.createItem(body);
        AllureUtils.attachJson("Response body", response.asPrettyString());

        assertEquals(200, response.statusCode());

        ItemDTO item = response.as(ItemDTO.class);

        assertNotNull(item.id());
        assertFalse(item.id().isBlank());

        assertEquals(sellerId, item.sellerId());
        assertNotNull(item.name());
        assertFalse(item.name().isBlank());

        assertNotNull(item.price());
        assertNotNull(item.statistics());
        assertNotNull(item.createdAt());
        assertFalse(item.createdAt().isBlank());
    }
}