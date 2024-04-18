package com.acme.conferencesystem.ticket.http;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.ticket.business.Ticket;
import com.acme.conferencesystem.ticket.business.TicketCategory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

import java.time.LocalDate;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@ApplicationModuleTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainerConfig.class)
class TicketControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void endToEndTest() {

        Ticket ticket = new Ticket(
                UUID.randomUUID(), TicketCategory.PENDING, LocalDate.now()
        );

        var newTicketId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(ticket)
                .when()
                .put("/tickets/buy")
                .then()
                .statusCode(201);


    }
}
