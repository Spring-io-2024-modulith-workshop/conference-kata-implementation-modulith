# Step 6 fixing dependencies

In this step, we are going to start fixing the dependencies.

## Modulith core Violations

Let's start running the test inside this class
/src/test/com/acme/conferencesystem/ApplicationStructure.java

```java

@Test
void verifyModularStructure() {
    modules.verify();
}
```

We will see the Module and method violations.

```text
org.springframework.modulith.core.Violations: - Module 'voting' depends on non-exposed type com.acme.conferencesystem.cfp.proposals.business.ProposalService within module 'cfp'!
```

## Fix module dependencies

We all are know the following principle:

```text
high-level modules should depend on abstractions rather than concrete
implementations.
```

Let's use interfaces to fix the dependencies.

```java
package com.acme.conferencesystem.cfp;

import java.util.List;
import java.util.UUID;

public interface ProposalInternalAPI {

    List<Proposal> getAcceptedProposals();

    Proposal getProposalById(UUID id);

    void validateProposalIsAccepted(UUID proposalId);

    void validateProposalIsNew(UUID proposalId);
}

```

- Create that interface at cfp level
- Implemented in ProposalService and use that

```java

@Service
public class ProposalService implements ProposalInternalAPI {
```

instead of ProposalService.
Where it's used.

If you are asking why at cfp level, and not inside proposals.
Is because
only first subpackages of the main root package `conferencesystem` are
exposed to the other packages.

## Run the test again

```java

@Test
void verifyModularStructure() {
    modules.verify();
}
```

And now let's get focus with UserService

copy into users module the following interface, and implemented in
UserService, and Use the interface instead of the concrete implementation
tin the rest of modules.

And issue way to know where UserSevice was use is execute the test and find
the first module that depends on UserService.

```text
org.springframework.modulith.core.Violations: - Module 'cfp' depends on non-exposed type com.acme.conferencesystem.users.business.UserService within module 'users'!

```

When changed run the test again.
