package com.acme.conferencesystem.cfp;

import com.acme.conferencesystem.cfp.talks.business.Talk;
import java.util.List;

public interface TalkInternalAPI {

    List<Talk> getTalks();

}
