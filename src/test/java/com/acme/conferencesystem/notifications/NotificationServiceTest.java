package com.acme.conferencesystem.notifications;

import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.Proposal;
import com.acme.conferencesystem.cfp.ProposalAcceptedEvent;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.ApplicationModuleTest.BootstrapMode;
import org.springframework.modulith.test.Scenario;

@ApplicationModuleTest(value = BootstrapMode.STANDALONE)
@Import(ContainerConfig.class)
class NotificationServiceTest {

    @Test
    void receiveProposalAcceptedEvent(Scenario scenario) {
        var proposal = Instancio.create(Proposal.class);

        scenario.publish(new ProposalAcceptedEvent(proposal))
                .andWaitForEventOfType(ProposalAcceptedEvent.class)
                .toArrive();
    }

}