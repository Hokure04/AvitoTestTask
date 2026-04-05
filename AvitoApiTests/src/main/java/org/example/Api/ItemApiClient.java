package org.example.Api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Dto.ItemRequest;

import static io.restassured.RestAssured.given;

public class ItemApiClient {

    public Response createItem(String body){
        return RestAssured.given()
                .body(body)
                .post("/api/1/item");
    }

    public Response getItemById(String id){
        return RestAssured.given()
                .get("/api/1/item/{id}", id);
    }

    public Response getItemBySellerId(String sellerId){
        return RestAssured.given()
                .get("/api/1/{sellerId}/item", sellerId);
    }

    public Response getStatisticsById(String id){
        return RestAssured.given()
                .get("/api/1/statistic/{id}", id);
    }

    public Response deleteItemById(String id){
        return RestAssured.given()
                .delete("/api/2/item/{id}", id);
    }
}
