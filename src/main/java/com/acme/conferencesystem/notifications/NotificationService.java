package com.acme.conferencesystem.notifications;

import com.acme.conferencesystem.cfp.proposals.events.ProposalAcceptedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    @EventListener
    void onProposalAcceptedEvent(ProposalAcceptedEvent proposalAcceptedEvent) {
        LOG.info("onProposalAcceptedEvent(proposalId={})", proposalAcceptedEvent.proposal().id());
    }
}
