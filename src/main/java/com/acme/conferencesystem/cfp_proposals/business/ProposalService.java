package com.acme.conferencesystem.cfp_proposals.business;

import com.acme.conferencesystem.cfp_proposals.ProposalInternalAPI;
import com.acme.conferencesystem.cfp_proposals.persistence.ProposalEntity;
import com.acme.conferencesystem.cfp_proposals.persistence.ProposalRepository;
import com.acme.conferencesystem.users.UserInternalAPI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.acme.conferencesystem.cfp_proposals.business.ProposalStatus.ACCEPTED;
import static com.acme.conferencesystem.cfp_proposals.business.ProposalStatus.NEW;

@Service
public class ProposalService implements ProposalInternalAPI {

    private final ProposalRepository repository;
    private final ProposalMapper mapper;
    private final UserInternalAPI userInternalAPI;

    public ProposalService(ProposalRepository repository, ProposalMapper mapper, UserInternalAPI userInternalAPI) {
        this.repository = repository;
        this.mapper = mapper;
        this.userInternalAPI = userInternalAPI;
    }

    public List<Proposal> getAllProposals() {
        List<Proposal> list = new ArrayList<>();
        for (ProposalEntity proposalEntity : repository.findAll()) {
            Proposal proposal = mapper.entityToProposal(proposalEntity);
            list.add(proposal);
        }
        return list;
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
        if (getProposalById(proposalId).status() != ACCEPTED) {
            throw new IllegalArgumentException("Proposal %s is not accepted".formatted(proposalId));
        }
    }

    @Override
    public void validateProposalIsNew(UUID proposalId) {
        if (getProposalById(proposalId).status() != NEW) {
            throw new IllegalArgumentException("Proposal %s is not accepted".formatted(proposalId));
        }
    }

    public Proposal approveProposal(UUID id) {
        return repository
                .findById(id)
                .map(ProposalEntity::ofAccepted)
                .map(repository::save)
                .map(mapper::entityToProposal)
                .orElseThrow();
    }

    public Proposal rejectProposal(UUID id) {
        return repository
                .findById(id)
                .map(ProposalEntity::ofRejected)
                .map(repository::save)
                .map(mapper::entityToProposal)
                .orElseThrow();
    }

}
