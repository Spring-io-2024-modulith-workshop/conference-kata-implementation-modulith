package com.acme.conferencesystem.cfp.talks.http;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.ProposalTestAPI;
import com.acme.conferencesystem.users.UsersTestAPI;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
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
        // Speaker 1 = 1 Talk
        UUID speakerId1 = UsersTestAPI.createSpeaker(requestSpecification);
        ProposalTestAPI.createAcceptedProposal(speakerId1, requestSpecification);
        ProposalTestAPI.createProposal(speakerId1, requestSpecification);

        // Speaker 2 = 2 Talks
        UUID speakerId2 = UsersTestAPI.createSpeaker(requestSpecification);
        ProposalTestAPI.createAcceptedProposal(speakerId2, requestSpecification);
        ProposalTestAPI.createAcceptedProposal(speakerId2, requestSpecification);
        ProposalTestAPI.createProposal(speakerId2, requestSpecification);

//        // Get all talks
//        given(requestSpecification)
//                .when()
//                .get("/talks")
//                .then()
//                .statusCode(200)
//                .body("$", instanceOf(List.class))
//                .body("findAll { it.speakerId == '" + speakerId1 + "' }.size()", equalTo(1))
//                .body("findAll { it.speakerId == '" + speakerId2 + "' }.size()", equalTo(2));

        // Get all talks
        Response response = given(requestSpecification)
                .when()
                .get("/talks");

        assertThat(response.statusCode()).isEqualTo(200);

        List<Map<String, Object>> talks = response.jsonPath().getList("$");
        assertThat(talks).isNotEmpty();
        long speaker1TalksCount = talks.stream().filter(talk -> talk.get("speakerId").equals(speakerId1.toString())).count();
        long speaker2TalksCount = talks.stream().filter(talk -> talk.get("speakerId").equals(speakerId2.toString())).count();
        assertThat(speaker1TalksCount).isEqualTo(1);
        assertThat(speaker2TalksCount).isEqualTo(2);
    }


}
