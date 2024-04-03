package com.acme.conferencesystem.cfp.proposals.http;

import com.acme.conferencesystem.cfp.proposals.business.Proposal;
import com.acme.conferencesystem.cfp.proposals.business.ProposalService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Collections;
import java.util.UUID;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProposalControllerEndToEndTest {

    @LocalServerPort
    private int port;

    @MockBean
    private ProposalService proposalService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void endToEndTest() {
        Proposal proposal = new Proposal(UUID.randomUUID(), "Test Proposal", "This is a test proposal.", "John Doe");
        BDDMockito.given(proposalService.getAllProposals()).willReturn(Collections.singletonList(proposal));
        BDDMockito.given(proposalService.getProposalById(proposal.id())).willReturn(java.util.Optional.of(proposal));

        // Submit proposal
        given()
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
