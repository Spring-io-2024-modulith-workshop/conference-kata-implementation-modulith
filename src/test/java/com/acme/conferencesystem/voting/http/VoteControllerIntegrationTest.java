package com.acme.conferencesystem.voting.http;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp_proposals.business.Proposal;
import com.acme.conferencesystem.users.business.User;
import com.acme.conferencesystem.users.business.UserRole;
import com.acme.conferencesystem.voting.business.Vote;
import io.restassured.http.ContentType;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

import java.util.UUID;

import static com.acme.conferencesystem.cfp_proposals.business.ProposalStatus.NEW;
import static io.restassured.RestAssured.given;
import static org.instancio.Select.field;

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
        //todo
        // 1. create an atendee
        // 2. create a proposal
        // 3. approve the proposal
        // 4. create a vote
        // 5. check that the vote is stored

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
}
