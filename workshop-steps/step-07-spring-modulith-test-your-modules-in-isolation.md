# Test your modules in isolation

## Let's start with the `proposals` module

1. Run the tests within this java file
   [`ProposalControllerIntegrationTest.java`](../src/test/java/com/acme/conferencesystem/cfp/proposals/http/ProposalControllerIntegrationTest.java).
2. And you'll get the following error:

    ```text
   Parameter 2 of constructor in com.acme.conferencesystem.cfp.proposals.business.ProposalService required a bean of type 'com.acme.conferencesystem.users.UserInternalAPI' that could not be found.
    ```
3. Change the `@ApplicationModuleTest` annotation to:

```java

@ApplicationModuleTest(mode = BootstrapMode.DIRECT_DEPENDENCIES, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainerConfig.class)
class ProposalControllerIntegrationTest extends AbstractIntegrationTest {
}
```

### Example 2

```java

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@Import(ContainerConfig.class)
class ProposalRepositoryTest {
}
```

### Example 3

```java 

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.
        DIRECT_DEPENDENCIES, webEnvironment = SpringBootTest.WebEnvironment.
        RANDOM_PORT)
@Import(ContainerConfig.class)
class VoteControllerIntegrationTest extends AbstractIntegrationTest {
}
```

   That will bootstrap `proposals` module with their direct dependencies.
4. Run the tests again.

## Repeat the same steps with the following tests

1. [ProposalRepositoryTest](../src/test/java/com/acme/conferencesystem/cfp/proposals/persistence/ProposalRepositoryTest.java)
2. [UserRepositoryTest](../src/test/java/com/acme/conferencesystem/users/persistence/UserRepositoryTest.java)
3. [TicketRepository](../src/test/java/com/acme/conferencesystem/users/persistence/UserRepositoryTest.java)
4. [VoteControllerIntegrationTest](../src/test/java/com/acme/conferencesystem/users/persistence/UserRepositoryTest.java)
5. [VoteRepositoryTest](../src/test/java/com/acme/conferencesystem/voting/persistence/VoteRepositoryTest.java)
6. [TalkControllerIntegrationTest](../src/test/java/com/acme/conferencesystem/cfp/talks/http/TalkControllerIntegrationTest.java)
7. [UserControllerIntegrationTest](../src/test/java/com/acme/conferencesystem/users/http/UserControllerIntegrationTest.java)
8. [NotificationServiceTest](../src/test/java/com/acme/conferencesystem/notifications/NotificationServiceTest.java)


