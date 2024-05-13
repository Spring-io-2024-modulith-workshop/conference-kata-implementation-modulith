package com.acme.conferencesystem.cfp.talks.http;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.ProposalTestAPI;
import com.acme.conferencesystem.users.UsersTestAPI;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.ApplicationModuleTest.BootstrapMode;

@ApplicationModuleTest(
        mode = BootstrapMode.ALL_DEPENDENCIES,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(ContainerConfig.class)
class TalkControllerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void retrieveTalks() {
        UUID speakerId1 = UsersTestAPI.createSpeaker(requestSpecification);
        UUID acceptedProposalId1_1 = ProposalTestAPI.createAcceptedProposal(speakerId1, requestSpecification);

        UUID speakerId2 = UsersTestAPI.createSpeaker(requestSpecification);
        UUID acceptedProposalId2_1 = ProposalTestAPI.createAcceptedProposal(speakerId2, requestSpecification);
        UUID acceptedProposalId2_2 = ProposalTestAPI.createAcceptedProposal(speakerId2, requestSpecification);
        UUID notAcceptedProposalId2_3 = ProposalTestAPI.createProposal(speakerId2, requestSpecification);

        // Get all talks
        given(requestSpecification)
                .when()
                .get("/talks")
                .then()
                .statusCode(200)
                .body("$", instanceOf(List.class))
                .body("findAll { it.speakerId == '" + speakerId1 + "' }.size()", equalTo(1))
                .body("findAll { it.speakerId == '" + speakerId2 + "' }.size()", equalTo(2));
    }


}
