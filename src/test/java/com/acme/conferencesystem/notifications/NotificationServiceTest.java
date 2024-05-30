package com.acme.conferencesystem.notifications;

import static org.assertj.core.api.Assertions.assertThat;

import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.Proposal;
import com.acme.conferencesystem.cfp.ProposalAcceptedEvent;
import com.acme.conferencesystem.notifications.NotificationServiceTest.TestNotificationService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.stereotype.Service;

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@Import({ContainerConfig.class, TestNotificationService.class})
class NotificationServiceTest {

    private static CountDownLatch latch;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private TestNotificationService notificationService;

    @BeforeEach
    void setUp() {
        latch = new CountDownLatch(1);
    }

    @Test
    void receiveProposalAcceptedEvent() throws InterruptedException {
        var proposal = Instancio.create(Proposal.class);
        ProposalAcceptedEvent event = new ProposalAcceptedEvent(proposal);

        eventPublisher.publishEvent(event);

        boolean eventReceived = latch.await(5, TimeUnit.SECONDS);
        assertThat(eventReceived).isTrue();
    }

    @Service
    @Primary
    static class TestNotificationService extends NotificationService {

        @Override
        void onProposalAcceptedEvent(ProposalAcceptedEvent proposalAcceptedEvent) {
            super.onProposalAcceptedEvent(proposalAcceptedEvent);
            latch.countDown();
        }
    }
}
