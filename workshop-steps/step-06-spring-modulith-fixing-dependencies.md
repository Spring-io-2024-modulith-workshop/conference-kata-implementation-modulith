# Step 6 fixing dependencies

In this step, we are going to start fixing the structural dependency exceptions.

## Core Spring Modulith Structure Violations

1. Begin running the test `verifyModularStructure` within this class:
   /src/test/com/acme/conferencesystem/ApplicationStructure.java

```java

@Test
void verifyModularStructure() {
    modules.verify();
}
```

We will see the output with Module and method exceptions.

```text
org.springframework.modulith.core.Violations: - Module 'voting' depends on non-exposed type com.acme.conferencesystem.cfp.proposals.business.ProposalService within module 'cfp'!
```

## Fix module dependencies

We all know the following principle:

```text
high-level modules should depend on abstractions rather than concrete
implementations.
```

Let's use interfaces to correctly expose the module dependencies.

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

1. Create that interface at `cfp` module level
2. Make ProposalService implement ProposalInternalAPI.

```java

@Service
public class ProposalService implements ProposalInternalAPI {
```

3. Use ProposalInternalAPI instead of ProposalService in the rest of modules.
   To avoid depending on a concrete implementation.

   Remember that only the first root module level is exposed in Spring
   Modulith, at lease for the current version of Spring Modulith (1.2.0)

   And that for the moment submodules / subpackages are not exposed.

## Run the test again to continue solving structural exceptions.

And now let's get focus with UserService

```java
package com.acme.conferencesystem.users;

import com.acme.conferencesystem.users.business.User;

import java.util.Optional;
import java.util.UUID;

public interface UserInternalAPI {

    void validateUserIsOrganizer(UUID userId);

    void validateUser(UUID userId);

    Optional<User> getUserById(UUID id);
}

```

1. Copy into `users` module the interface, and implement it in
   `UserService`, then use the interface instead of the concrete
   implementation
   within the rest of modules.

   And easy way to know where `UserSevice` is used, is to execute the test and
   see the first exception that depends on UserService.

```text
org.springframework.modulith.core.Violations: - Module 'cfp' depends on non-exposed type com.acme.conferencesystem.users.business.UserService within module 'users'!
```

## Run the test again to get the next structural exception

1. Run the test again. Then you'll get the following exception:

```text
org.springframework.modulith.core.Violations: - Module 'notifications' depends on non-exposed type com.acme.conferencesystem.cfp.proposals.events.ProposalAcceptedEvent within module 'cfp'!
```

2. Identify that the module has the required dependency
   `ProposalAcceptedEvent` that is not correcly exposed.

3. Move `ProposalAcceptedEvent` in `cfp` module level.

4. Run the test again.

## Run the test again and fix the nex exception

1. Run the test again. Then you'll get the following exception:

```text
Module 'notifications' depends on non-exposed type com.acme.conferencesystem.cfp.Proposal within module 'cfp'!
```

2. Identify that the module has the required dependency
   `Proposal` that is not correcly exposed.
3. Move `Proposal` in `cfp` module level.
4. Run the test again.

## Now we are done! Let's move on to step 7
