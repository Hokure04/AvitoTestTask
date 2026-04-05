package org.example.Api.tests;

import io.restassured.response.Response;
import org.example.Api.BaseApiTest;
import org.example.Api.ItemApiClient;
import org.example.Dto.ItemDTO;
import org.example.Dto.ItemRequest;
import org.example.Dto.StatisticsDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateItemTests extends BaseApiTest {

    private final ItemApiClient itemApiClient = new ItemApiClient();

    @Test
    public void createItemShouldReturnCreatedItemAccordingToContract() {
        ItemRequest request = new ItemRequest(
                123456,
                "Test item",
                1000,
                new StatisticsDTO(1, 10, 2)
        );

        Response response = itemApiClient.createItem(request);

        assertEquals(200, response.statusCode());

        ItemDTO item = response.as(ItemDTO.class);

        assertNotNull(item.id());
        assertEquals(request.sellerId(), item.sellerId());
        assertEquals(request.name(), item.name());
        assertEquals(request.price(), item.price());
        assertNotNull(item.statistics());
        assertNotNull(item.createdAt());
    }
}