package com.acme.conferencesystem.cfp;

import static io.restassured.RestAssured.given;
import static org.instancio.Select.field;

import com.acme.conferencesystem.cfp.proposals.business.ProposalStatus;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.UUID;
import org.instancio.Instancio;
import org.springframework.stereotype.Component;

@Component
public class ProposalTestAPI {

    public static List<Proposal> getAllProposals(RequestSpecification requestSpecification) {
        return given(requestSpecification)
                .when()
                .get("/proposals")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<>() {
                });
    }

    public static UUID createAcceptedProposal(UUID speakerId, RequestSpecification requestSpecification) {
        UUID proposalId = createProposal(speakerId, requestSpecification);
        acceptProposal(proposalId, requestSpecification);
        return proposalId;
    }

    public static UUID createProposal(UUID speakerId, RequestSpecification requestSpecification) {
        var proposal = Instancio.of(Proposal.class)
                .ignore(field(Proposal::id))
                .set(field(Proposal::speakerId), speakerId)
                .set(field(Proposal::status), ProposalStatus.NEW)
                .create();

        return submitProposal(proposal, requestSpecification);
    }

    public static UUID submitProposal(Proposal proposal, RequestSpecification requestSpecification) {
        String proposalIdString = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(proposal)
                .when()
                .post("/proposals")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
        return UUID.fromString(proposalIdString);
    }

    private static void acceptProposal(UUID proposalId, RequestSpecification requestSpecification) {
        given(requestSpecification)
                .when()
                .patch("/proposals/{proposalId}/approve", proposalId)
                .then()
                .statusCode(200);
    }
}
