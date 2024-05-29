package com.acme.conferencesystem.cfp.proposals.http;

import com.acme.conferencesystem.cfp.Proposal;
import com.acme.conferencesystem.cfp.proposals.business.ProposalService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/proposals")
class ProposalController {

    private final ProposalService proposalService;

    ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @GetMapping
    ResponseEntity<List<Proposal>> getAllProposals() {
        List<Proposal> proposals = proposalService.getAllProposals();
        return ResponseEntity.ok(proposals);
    }

    @PostMapping
    ResponseEntity<Proposal> submitProposal(@Valid @RequestBody Proposal proposal) {
        try {
            Proposal submittedProposal = proposalService.submitProposal(proposal);
            return new ResponseEntity<>(submittedProposal, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Proposal> getProposalById(@PathVariable UUID id) {
        return ResponseEntity.ok(proposalService.getProposalById(id));
    }

    @PatchMapping("/{id}/approve")
    ResponseEntity<Proposal> approveProposal(@PathVariable UUID id) {
        return ResponseEntity.ok(proposalService.approveProposal(id));
    }

    @PatchMapping("/{id}/reject")
    ResponseEntity<Proposal> rejectProposal(@PathVariable UUID id) {
        return ResponseEntity.ok(proposalService.rejectProposal(id));
    }

}
