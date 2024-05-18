package com.acme.conferencesystem.notifications;

import com.acme.conferencesystem.cfp.ProposalAcceptedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    @ApplicationModuleListener
    void onProposalAcceptedEvent(ProposalAcceptedEvent proposalAcceptedEvent) {
        LOG.info("onProposalAcceptedEvent(proposalId={})", proposalAcceptedEvent.proposal().id());
    }
}
