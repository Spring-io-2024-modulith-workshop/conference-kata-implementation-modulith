package com.acme.conferencesystem.cfp_proposals.business;

import com.acme.conferencesystem.cfp_proposals.persistence.ProposalEntity;
import com.acme.conferencesystem.cfp_proposals.persistence.ProposalRepository;
import com.acme.conferencesystem.users.UserInternalAPI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProposalService {

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

    public Optional<Proposal> getProposalById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToProposal);
    }

}
