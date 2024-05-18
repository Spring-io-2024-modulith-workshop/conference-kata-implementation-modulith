package com.acme.conferencesystem.voting.http;

import static com.acme.conferencesystem.cfp.proposals.business.ProposalStatus.NEW;
import static io.restassured.RestAssured.given;
import static org.instancio.Select.field;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.Proposal;
import com.acme.conferencesystem.users.business.User;
import com.acme.conferencesystem.users.business.UserRole;
import com.acme.conferencesystem.voting.business.Vote;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest(
        mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(ContainerConfig.class)
class VoteControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void voteProposal() {
        //todo
        // 1. create an organizer
        User user = Instancio.of(User.class)
                .ignore(field(User::id))
                .set(field(User::role), UserRole.ORGANIZER)
                .create();

        // Submit user
        var newUserId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // 2. create a proposal
        Proposal proposal = Instancio
                .of(Proposal.class)
                .ignore(field(Proposal::id))
                .set(field(Proposal::speakerId), UUID.fromString(newUserId.toString()))
                .set(field(Proposal::status), NEW)
                .create();
        var proposalPersistedId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(proposal)
                .when()
                .post("/proposals")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // 3. create a vote
        Vote vote = Instancio.of(Vote.class)
                .ignore(field(Vote::id))
                .set(field(Vote::userId), UUID.fromString(newUserId.toString()))
                .set(field(Vote::proposalId), UUID.fromString(proposalPersistedId.toString()))
                .create();

        given(requestSpecification)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(vote)
                .when()
                .post("/voting/proposal")
                .then()
                .statusCode(200);
    }

    @Test
    void voteTalk() {
        //<editor-fold desc="Create attendee">
        User user = Instancio.of(User.class)
                .ignore(field(User::id))
                .set(field(User::role), UserRole.ATTENDEE)
                .create();

        var newUserId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
        //</editor-fold>

        //<editor-fold desc="Create proposal">
        Proposal proposal = Instancio
                .of(Proposal.class)
                .ignore(field(Proposal::id))
                .set(field(Proposal::speakerId), UUID.fromString(newUserId.toString()))
                .set(field(Proposal::status), NEW)
                .create();
        var proposalPersistedId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(proposal)
                .when()
                .post("/proposals")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        given(requestSpecification)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON).when().patch("proposals/{id}/approve", proposalPersistedId)
                .then()
                .statusCode(200);
        //</editor-fold>

        //<editor-fold desc="Vote">
        Vote vote = Instancio.of(Vote.class)
                .ignore(field(Vote::id))
                .set(field(Vote::userId), UUID.fromString(newUserId.toString()))
                .set(field(Vote::proposalId), UUID.fromString(proposalPersistedId.toString()))
                .create();

        given(requestSpecification)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(vote)
                .when()
                .post("/voting/talk")
                .then()
                .statusCode(200);
        //</editor-fold>
    }
}
