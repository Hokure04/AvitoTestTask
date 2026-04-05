package org.example.Api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {
    protected static final String BASE_URL = "https://qa-internship.avito.com";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "";
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.requestSpecification = RestAssured.given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

}
