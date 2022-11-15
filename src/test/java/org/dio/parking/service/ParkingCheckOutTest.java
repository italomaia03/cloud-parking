package org.dio.parking.service;

import io.restassured.RestAssured;
import org.dio.parking.controller.AbstractContainerBase;
import org.dio.parking.controller.dto.ParkingCreateDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingCheckOutTest extends AbstractContainerBase {
    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = this.randomPort;
    }

    @Test
    public void whenCheckOutCheckBill(){
        var createDto = new ParkingCreateDto();
        createDto.setColor("AMARELO");
        createDto.setLicense("QKI-5462");
        createDto.setModel("BRASILIA");
        createDto.setState("SP");

        var expectedBill = 0f;

        String id = RestAssured.given()
                .headers("Authorization", "Basic dXNlcjpTZW5oYUAxMjM=")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDto)
                .when()
                .post("/parking")
                .then()
                .extract()
                .path("id");

        RestAssured.given()
                .headers("Authorization", "Basic dXNlcjpTZW5oYUAxMjM=")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDto)
                .when()
                .pathParam("id", id)
                .post("/parking/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("bill", Matchers.equalTo(expectedBill));
    }
}
