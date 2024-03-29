package com.acme.conferencesystem.cfp.proposals.http;

import com.acme.conferencesystem.cfp.proposals.business.Proposal;
import com.acme.conferencesystem.cfp.proposals.business.ProposalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @GetMapping
    public ResponseEntity<List<Proposal>> getAllProposals() {
        List<Proposal> proposals = proposalService.getAllProposals();
        return ResponseEntity.ok(proposals);
    }

    @PostMapping
    public ResponseEntity<Proposal> submitProposal(@Valid @RequestBody Proposal proposal) {
        Proposal submittedProposal = proposalService.submitProposal(proposal);
        return new ResponseEntity<>(submittedProposal, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposal> getProposalById(@PathVariable UUID id) {
        return proposalService.getProposalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
