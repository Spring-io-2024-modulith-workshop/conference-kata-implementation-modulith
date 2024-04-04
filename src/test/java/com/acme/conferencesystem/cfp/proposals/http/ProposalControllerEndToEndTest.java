package com.acme.conferencesystem.cfp.proposals.http;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.cfp.proposals.business.Proposal;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class ProposalControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void endToEndTest() {
        Proposal proposal = new Proposal(UUID.randomUUID(), "Test Proposal", "This is a test proposal.", "John Doe");

        // Submit proposal
        given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(proposal)
                .when()
                .post("/proposals")
                .then()
                .statusCode(201);

        // Get all proposals
        get("/proposals")
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", equalTo(proposal.id().toString()))
                .body("[0].title", equalTo("Test Proposal"))
                .body("[0].description", equalTo("This is a test proposal."))
                .body("[0].speaker", equalTo("John Doe"));

        // Get proposal by ID
        get("/proposals/{id}", proposal.id())
                .then()
                .statusCode(200)
                .body("id", equalTo(proposal.id().toString()))
                .body("title", equalTo("Test Proposal"))
                .body("description", equalTo("This is a test proposal."))
                .body("speaker", equalTo("John Doe"));
    }

}
