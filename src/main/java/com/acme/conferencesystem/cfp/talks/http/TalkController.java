package com.acme.conferencesystem.cfp.talks.http;

import com.acme.conferencesystem.cfp.talks.business.Talk;
import com.acme.conferencesystem.cfp.talks.business.TalkService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/talks")
class TalkController {

    private final TalkService talkService;


    TalkController(TalkService talkService) {
        this.talkService = talkService;
    }

    @GetMapping
    ResponseEntity<List<Talk>> getTalks() {
        List<Talk> talks = talkService.getTalks();
        return ResponseEntity.ok(talks);
    }
}
