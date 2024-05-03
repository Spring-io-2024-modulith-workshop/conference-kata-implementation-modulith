package com.acme.conferencesystem.cfp_proposals.http;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp_proposals.business.Proposal;
import io.restassured.http.ContentType;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.instancio.Select.field;

@ApplicationModuleTest(
        mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(ContainerConfig.class)
class ProposalControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ApplicationEventPublisher publisher;


    @Test
    void endToEndTest() {
        UUID speakerId = createSpeaker();
        Proposal proposal = Instancio.of(Proposal.class)
                .ignore(field(Proposal::id))
                .set(field(Proposal::speakerId), speakerId)
                .create();

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
                .body("id", hasItem(newProposalId))
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

    private UUID createSpeaker() {
        String speakerIdString = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body("""
                        {
                             "name": "John Doe",
                             "email": "john@example.com",
                             "phone": "(555) 555-5551",
                             "role": "SPEAKER"
                         }""")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        return UUID.fromString(speakerIdString);
    }

    @Test
    void notAcceptProposalsWithInvalidSpeakerId(PublishedEvents event) {
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
