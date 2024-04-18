package com.acme.conferencesystem.cfp.proposals.http;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.proposals.business.Proposal;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ApplicationModuleTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainerConfig.class)
class ProposalControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void endToEndTest() {
        Proposal proposal = new Proposal(null, "Test Proposal", "This is a test proposal.", "John Doe");

        // Submit proposal
        var newProposalId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(proposal)
                .when()
                .post("/proposals")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Get all proposals
        given(requestSpecification)
                .when()
                .get("/proposals")
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", equalTo(newProposalId))
                .body("[0].title", equalTo("Test Proposal"))
                .body("[0].description", equalTo("This is a test proposal."))
                .body("[0].speaker", equalTo("John Doe"));

        // Get proposal by ID
        given(requestSpecification)
                .when()
                .get("/proposals/{id}", newProposalId)
                .then()
                .statusCode(200)
                .body("id", equalTo(newProposalId))
                .body("title", equalTo("Test Proposal"))
                .body("description", equalTo("This is a test proposal."))
                .body("speaker", equalTo("John Doe"));
    }

}
