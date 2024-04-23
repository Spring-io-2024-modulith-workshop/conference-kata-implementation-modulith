package com.acme.conferencesystem.cfp.proposals.http;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.proposals.business.Proposal;
import io.restassured.http.ContentType;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.instancio.Select.field;

@ApplicationModuleTest(
        mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(ContainerConfig.class)
class ProposalControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void endToEndTest() {
        Proposal proposal = Instancio.of(Proposal.class).ignore(field(Proposal::id)).create();

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
                .body("[0].title", equalTo(proposal.title()))
                .body("[0].description", equalTo(proposal.description()))
                .body("[0].speakerId", equalTo(proposal.speakerId().toString()));

        // Get proposal by ID
        given(requestSpecification)
                .when()
                .get("/proposals/{id}", newProposalId)
                .then()
                .statusCode(200)
                .body("id", equalTo(newProposalId))
                .body("title", equalTo(proposal.title()))
                .body("description", equalTo(proposal.description()))
                .body("speakerId", equalTo(proposal.speakerId().toString()));
    }

}
