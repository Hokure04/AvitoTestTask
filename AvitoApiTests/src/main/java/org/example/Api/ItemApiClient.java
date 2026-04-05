package org.example.Api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Dto.ItemRequest;

import static io.restassured.RestAssured.given;

public class ItemApiClient {

    public Response createItem(ItemRequest request){
        return RestAssured.given()
                .body(request)
                .post("api/1/item");
    }

    public Response getItemById(String id){
        return RestAssured.given()
                .get("api/1/item/{id}", id);
    }

    public Response getItemBySellerId(String sellerId){
        return RestAssured.given()
                .get("api/1/{sellerId}/item", sellerId);
    }

    public Response getStatisticsById(String id){
        return RestAssured.given()
                .get("api/1/statistics/{id}", id);
    }

    public Response deleteItemById(String id){
        return RestAssured.given()
                .delete("api/1/item/{id}", id);
    }
}
