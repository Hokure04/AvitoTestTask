package org.example.Api.tests;

import io.restassured.response.Response;
import org.example.Api.BaseApiTest;
import org.example.Api.ItemApiClient;
import org.example.Dto.ItemRequest;
import org.example.Dto.ItemResponse;
import org.example.Dto.StatisticsDTO;
import org.example.Utils.ResponseUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GetItemTests extends BaseApiTest {

    private final ItemApiClient itemApiClient = new ItemApiClient();

    @Test
    public void getItemByExistingIdShouldReturn200() {
        ItemRequest request = new ItemRequest(
                123458,
                "Item",
                2000,
                new StatisticsDTO(3, 30, 4)
        );

        Response createResponse = itemApiClient.createItem(request);
        assertEquals(200, createResponse.statusCode());

        String itemId = ResponseUtils.extractItemIdFromStatus(
                createResponse.as(ItemResponse.class).status()
        );

        Response getResponse = itemApiClient.getItemById(itemId);

        assertEquals(200, getResponse.statusCode());
        assertFalse(getResponse.asString().isBlank());
    }
}
