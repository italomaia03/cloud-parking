package org.dio.parking.controller;

import io.restassured.RestAssured;
import org.dio.parking.controller.dto.ParkingCreateDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;



//boa prática para não usar a porta 80 durante os testes de integração
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest extends AbstractContainerBase{

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = this.randomPort;
    }

    @Test
    void whenFindAllThenGetResult() {
        RestAssured.given()
                .headers("Authorization", "Basic dXNlcjpTZW5oYUAxMjM=")
                .when()
                .get("/parking")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void whenCreateThenCheckIsCreated() {
        var createDto = new ParkingCreateDto();
        createDto.setColor("AMARELO");
        createDto.setLicense("QKI-5462");
        createDto.setModel("BRASILIA");
        createDto.setState("SP");

        RestAssured.given()
                .headers("Authorization", "Basic dXNlcjpTZW5oYUAxMjM=")
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDto)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license",Matchers.equalTo("QKI-5462"));

    }

    @Test
    void whenFindByIdThenCheckFound() {
        var createDto = new ParkingCreateDto();
        createDto.setColor("AMARELO");
        createDto.setLicense("QKI-5462");
        createDto.setModel("BRASILIA");
        createDto.setState("SP");

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
                .when()
                .pathParam("id", id)
                .get("/parking/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("license", Matchers.equalTo("QKI-5462"));
    }

    @Test
    void whenDeleteThenCheckIsDeleted() {
        var createDto = new ParkingCreateDto();
        createDto.setColor("AMARELO");
        createDto.setLicense("QKI-5462");
        createDto.setModel("BRASILIA");
        createDto.setState("SP");

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
                .when()
                .pathParam("id", id)
                .delete("/parking/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void whenUpdateThenCheckIsUpdated() {
        var createDto = new ParkingCreateDto();
        createDto.setColor("AMARELO");
        createDto.setLicense("QKI-5462");
        createDto.setModel("BRASILIA");
        createDto.setState("SP");

        String id = RestAssured.given()
                .headers("Authorization", "Basic dXNlcjpTZW5oYUAxMjM=")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDto)
                .when()
                .post("/parking")
                .then()
                .extract()
                .path("id");

        createDto.setColor("AZUL");

        RestAssured.given()
                .headers("Authorization", "Basic dXNlcjpTZW5oYUAxMjM=")
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDto)
                .pathParam("id", id)
                .put("/parking/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("color", Matchers.equalTo("AZUL"));
    }
}