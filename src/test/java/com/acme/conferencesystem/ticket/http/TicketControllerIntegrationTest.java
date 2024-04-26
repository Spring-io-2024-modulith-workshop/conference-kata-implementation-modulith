package com.acme.conferencesystem.ticket.http;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.ticket.business.Ticket;
import com.acme.conferencesystem.ticket.business.TicketCategory;
import com.acme.conferencesystem.ticket.business.TicketStatus;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ApplicationModuleTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainerConfig.class)
class TicketControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void buyTicketTest() {

        Ticket ticket = new Ticket(
                null,
                TicketCategory.BLIND,
                LocalDateTime.now(),
                100D,
                TicketStatus.PENDING
        );

        // Submit Ticket
        var newTicketId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(ticket)
                .when()
                .post("/tickets/buy")
                .then()
                .statusCode(201).extract().path("id");

        //Get all tickets
        given(requestSpecification)
                .when()
                .get("/tickets")
                .then()
                .statusCode(200)
                .body("size()", is(1));

        //Get a ticket by id
        given(requestSpecification)
                .when()
                .get("/tickets/{id}", newTicketId)
                .then()
                .statusCode(200)
                .body("id", is(newTicketId))
                .body("price", is(ticket.price().floatValue()))
                .body("status", is(TicketStatus.CONFIRMED.toString()))
                .body("category", equalTo(TicketCategory.BLIND.toString()));
    }

    @Test
    void bookTicketAndConfirmTest() {

        Ticket ticket = new Ticket(
                null,
                TicketCategory.REGULAR,
                LocalDateTime.now(),
                100D,
                TicketStatus.PENDING
        );

        // Book Ticket
        var newTicketId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(ticket)
                .when()
                .post("/tickets/book")
                .then()
                .statusCode(201)
                .extract().path("id");

        //Confirm ticket
        given(requestSpecification)
                .when()
                .patch("/tickets/{id}/confirm", newTicketId)
                .then()
                .statusCode(200);
    }

    @Test
    void bookTicketAndCancelTest() {

        Ticket ticket = new Ticket(
                null,
                TicketCategory.REGULAR,
                LocalDateTime.now(),
                100D,
                TicketStatus.PENDING
        );

        // Book Ticket
        var newTicketId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(ticket)
                .when()
                .post("/tickets/book")
                .then()
                .statusCode(201)
                .extract().path("id");

        //Confirm ticket
        given(requestSpecification)
                .when()
                .patch("/tickets/{id}/cancel", newTicketId)
                .then()
                .statusCode(200);
    }
}
