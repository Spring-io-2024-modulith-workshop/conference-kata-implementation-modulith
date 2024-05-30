# Spring modulith Verify Application module structure

Spring modulith internally uses jMolecules to verify the module structure of an
application.

## The module / architecture test

- [ ] Add ApplicationStructure.java

Let's add this class into our `/src/test/com/acme/conferencesystem/
ApplicationStructure.java`

```java
package com.acme.conferencesystem;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ArchitectureTests {

    ApplicationModules modules = ApplicationModules.of(Application.class);

    @Test
    void printModuleArrangement() {
        modules.forEach(System.out::println);
    }

    @Test
    void verifyModularStructure() {
        modules.verify();
    }

    @Test
    void writeDocumentation() {
        new Documenter(modules)
                .writeDocumentation()
                .writeIndividualModulesAsPlantUml();
    }

}
```

## Execute the three tests one by one

- [ ] Execute `printModuleArrangement()`

`modules.forEach(System.out::println);`

You are going to get the following output: A list of modules and the beans
that they use, and their logical name.

```text
# Ticket
> Logical name: ticket
> Base package: com.acme.conferencesystem.ticket
> Spring beans:
  o ….business.TicketMapperImpl
  o ….business.TicketService
  o ….http.TicketController
  o ….persistence.TicketRepository

# Voting
> Logical name: voting
> Base package: com.acme.conferencesystem.voting
> Spring beans:
  o ….business.VoteMapperImpl
  o ….business.VoteService
  o ….http.VoteController
  o ….persistence.VoteRepository

# Cfp
> Logical name: cfp
> Base package: com.acme.conferencesystem.cfp
> Spring beans:
  o ….proposals.business.ProposalMapperImpl
  o ….proposals.business.ProposalService
  o ….proposals.http.ProposalController
  o ….proposals.persistence.ProposalRepository
  o ….talks.business.TalkMapperImpl
  o ….talks.business.TalkService
  o ….talks.http.TalkController

# Users
> Logical name: users
> Base package: com.acme.conferencesystem.users
> Spring beans:
  o ….business.UserMapperImpl
  o ….business.UserService
  o ….http.UserController
  o ….persistence.UsersRepository

# Notifications
> Logical name: notifications
> Base package: com.acme.conferencesystem.notifications
> Spring beans:
  + ….NotificationService
```

- [ ] Execute `verifyModularStructure()` you are going to lunch the jMolecules
  verification

`modules.verify();`

You will see that we found modules that don't correctly expose the
dependencies to the other modules.
We will fix that later.

```text
org.springframework.modulith.core.Violations: - Module 'voting' depends on non-exposed type com.acme.conferencesystem.cfp.proposals.business.ProposalService within module 'cfp'!
VoteService declares constructor VoteService(VoteRepository, VoteMapper, ProposalService, UserService) in (VoteService.java:0)
```

- [ ] Execute `writeDocumentation()` it will automatically write the
  documentation
  that can be found
  at [components.puml](/target/spring-modulith-docs/components.puml) with the C4
  model diagrams.

![step-04-c4.png](img/step-04-c4.png)
