package com.acme.conferencesystem.cfp.proposals.http;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.instancio.Select.field;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.Proposal;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalRepository;
import com.acme.conferencesystem.users.UsersTestAPI;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest(
        mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(ContainerConfig.class)
class ProposalControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ProposalRepository repository;

    @Test
    void endToEndTest() {
        UUID speakerId = UsersTestAPI.createSpeaker(requestSpecification);
        Proposal proposal = Instancio.of(Proposal.class)
                .ignore(field(Proposal::id))
                .set(field(Proposal::speakerId), speakerId)
                .create();

        // Submit proposal
        String newProposalId = given(requestSpecification)
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
                .body("$", instanceOf(List.class));

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

    @Test
    void notAcceptProposalsWithInvalidSpeakerId() {
        Proposal proposal = Instancio.of(Proposal.class).ignore(field(Proposal::id)).create();
        // Submit proposal
        given(requestSpecification)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(proposal)
                .when()
                .post("/proposals")
                .then()
                .statusCode(400)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo("User with ID " + proposal.speakerId() + ", is not valid"));
    }

}
