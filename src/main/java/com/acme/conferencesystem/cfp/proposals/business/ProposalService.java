package com.acme.conferencesystem.cfp.proposals.business;

import com.acme.conferencesystem.UserValidationEvent;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProposalService {

    private final ProposalRepository repository;
    private final ProposalMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public ProposalService(ProposalRepository repository, ProposalMapper mapper, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    public List<Proposal> getAllProposals() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::entityToProposal)
                .collect(Collectors.toList());
    }

    @Transactional
    public Proposal submitProposal(Proposal proposal) {
        eventPublisher.publishEvent(new UserValidationEvent(proposal.speakerId()));

        var proposalEntity = mapper.proposalToEntity(proposal);
        var submittedProposalEntity = repository.save(proposalEntity);
        return mapper.entityToProposal(submittedProposalEntity);
    }

    public Optional<Proposal> getProposalById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToProposal);
    }

}
