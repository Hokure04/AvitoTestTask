package org.example.Api.tests;

import io.restassured.response.Response;
import org.example.Api.BaseApiTest;
import org.example.Api.ItemApiClient;
import org.example.Dto.ItemResponse;
import org.example.Utils.ItemDataProvider;
import org.example.Utils.ResponseUtils;
import org.example.Utils.SellerIdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetItemTests extends BaseApiTest {

    private final ItemApiClient itemApiClient = new ItemApiClient();

    @Test
    public void getItemByIdSuccessfully() {
        int sellerId = SellerIdGenerator.generateSellerId();

        String body = ItemDataProvider.validItem(sellerId);

        Response createResponse = itemApiClient.createItem(body);
        assertEquals(200, createResponse.statusCode());

        ItemResponse itemResponse = createResponse.as(ItemResponse.class);
        String itemId = ResponseUtils.extractItemIdFromStatus(itemResponse.status());

        Response getResponse = itemApiClient.getItemById(itemId);

        assertEquals(200, getResponse.statusCode());
        assertFalse(getResponse.asString().isBlank());
        assertTrue(getResponse.asString().contains(itemId));
    }
}
