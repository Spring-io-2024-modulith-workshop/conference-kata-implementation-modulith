package com.acme.conferencesystem.voting.http;

import com.acme.conferencesystem.voting.business.Vote;
import com.acme.conferencesystem.voting.business.VoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voting")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/proposal")
    public ResponseEntity<Vote> voteProposal(@Valid @RequestBody Vote vote) {
        return ResponseEntity.ok(voteService.voteProposal(vote));
    }

    @PostMapping("/talk")
    public ResponseEntity<Vote> voteTalk(@Valid @RequestBody Vote vote) {
        return ResponseEntity.ok(voteService.voteTalk(vote));
    }

}
