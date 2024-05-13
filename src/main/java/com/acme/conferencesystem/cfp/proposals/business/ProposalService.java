package com.acme.conferencesystem.cfp.proposals.business;

import com.acme.conferencesystem.cfp.ProposalInternalAPI;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalEntity;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalRepository;
import com.acme.conferencesystem.users.UserInternalAPI;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProposalService implements ProposalInternalAPI {

    private final ProposalRepository repository;
    private final ProposalMapper mapper;
    private final UserInternalAPI userInternalAPI;

    ProposalService(ProposalRepository repository, ProposalMapper mapper, UserInternalAPI userInternalAPI) {
        this.repository = repository;
        this.mapper = mapper;
        this.userInternalAPI = userInternalAPI;
    }

    public List<Proposal> getAllProposals() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(mapper::entityToProposal)
                .toList();
    }

    public List<Proposal> getAcceptedProposals() {
        return repository.getProposalEntityByStatus(ProposalStatus.ACCEPTED).stream().map(mapper::entityToProposal).toList();
    }

    @Transactional
    public Proposal submitProposal(Proposal proposal) {
        userInternalAPI.validateUser(proposal.speakerId());

        var proposalEntity = mapper.proposalToEntity(proposal);
        var submittedProposalEntity = repository.save(proposalEntity);
        return mapper.entityToProposal(submittedProposalEntity);
    }

    @Override
    public Proposal getProposalById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToProposal)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void validateProposalIsAccepted(UUID proposalId) {
        if (getProposalById(proposalId).status() != ProposalStatus.ACCEPTED) {
            throw new IllegalArgumentException("Proposal %s is not accepted".formatted(proposalId));
        }
    }

    @Override
    public void validateProposalIsNew(UUID proposalId) {
        if (getProposalById(proposalId).status() != ProposalStatus.NEW) {
            throw new IllegalArgumentException("Proposal %s is not accepted".formatted(proposalId));
        }
    }

    public Proposal approveProposal(UUID id) {
        return repository
                .findById(id)
                .map(ProposalEntity::createWithAcceptedStatus)
                .map(repository::save)
                .map(mapper::entityToProposal)
                .orElseThrow();
    }

    public Proposal rejectProposal(UUID id) {
        return repository
                .findById(id)
                .map(ProposalEntity::createWithRejectedStatus)
                .map(repository::save)
                .map(mapper::entityToProposal)
                .orElseThrow();
    }

}
