package com.acme.conferencesystem.cfp.proposals.business;

import com.acme.conferencesystem.cfp.proposals.persistence.ProposalEntity;
import com.acme.conferencesystem.cfp.proposals.persistence.ProposalRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProposalService {

    private final ProposalRepository repository;
    private final ProposalMapper mapper;

    public ProposalService(ProposalRepository repository, ProposalMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Proposal> getAllProposals() {
        List<Proposal> list = new ArrayList<>();
        for (ProposalEntity proposalEntity : repository.findAll()) {
            Proposal proposal = mapper.entityToProposal(proposalEntity);
            list.add(proposal);
        }
        return list;
    }

    public Proposal submitProposal(Proposal proposal) {
        var proposalEntity = mapper.proposalToEntity(proposal);
        var submittedProposalEntity = repository.save(proposalEntity);
        return mapper.entityToProposal(submittedProposalEntity);
    }

    public Optional<Proposal> getProposalById(UUID id) {
        return repository.findById(id)
                .map(mapper::entityToProposal);
    }

}
