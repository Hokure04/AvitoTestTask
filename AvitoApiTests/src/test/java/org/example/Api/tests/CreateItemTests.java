package org.example.Api.tests;

import io.restassured.response.Response;
import org.example.Api.BaseApiTest;
import org.example.Api.ItemApiClient;
import org.example.Dto.ItemResponse;
import org.example.Utils.ItemDataProvider;
import org.example.Utils.SellerIdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreateItemTests extends BaseApiTest {

    private final ItemApiClient itemApiClient = new ItemApiClient();

    @Test
    void CreateItemSuccessfully() {
        int sellerId = SellerIdGenerator.generateSellerId();

        String body = ItemDataProvider.validItem(sellerId);
        System.out.println(body);

        Response response = itemApiClient.createItem(body);

        assertEquals(200, response.statusCode());

        ItemResponse itemResponse = response.as(ItemResponse.class);
        assertNotNull(itemResponse.status());
        assertFalse(itemResponse.status().isBlank());
    }
}