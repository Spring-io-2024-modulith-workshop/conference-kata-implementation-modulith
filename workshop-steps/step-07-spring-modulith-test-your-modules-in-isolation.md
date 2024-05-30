# Test your modules in isolation

## Let's start by `proposals` module

1. Run the tests within this java file
   `ProposalControllerIntegrationTest.java`:
2. And you'll get the following error:

    ```text
   Parameter 2 of constructor in com.acme.conferencesystem.cfp.proposals.business.ProposalService required a bean of type 'com.acme.conferencesystem.users.UserInternalAPI' that could not be found.
    ```
3. Change the `@ApplicationModuleTest` annotation to:
    ```java
   @ApplicationModuleTest(mode = BootstrapMode.DIRECT_DEPENDENCIES, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
   ```
   That will bootstrap `proposals` module with their direct dependencies.
4. Run the tests again.

## Repeat the same steps with the following tests

1. ProposalRepositoryTest
2. UserRepositoryTest
3. TicketRepository
4. VoteControllerIntegrationTest
5. VoteRepositoryTest
6. TalkContollerIntegrationTest
7. UserControllerIntegrationTest
8. NotificationServiceTest

```java
@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
```

or

```java 
@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.
        DIRECT_DEPENDENCIES, webEnvironment = SpringBootTest.WebEnvironment.
        RANDOM_PORT)
```
