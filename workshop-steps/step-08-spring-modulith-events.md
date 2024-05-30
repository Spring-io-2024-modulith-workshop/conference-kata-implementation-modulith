# Spring modulith events

## Reference about : Application Module Listener

To run a transactional event listener in a transaction itself, it would need to
be annotated with `@Transactional` in turn.

An async, transactional event listener running in a transaction itself

```java

@Component
class InventoryManagement {

    @Async
    @Transactional
    @TransactionalEventListener
    void on(OrderCompleted event) { /* … */ }
}
```

To ease the declaration of what is supposed to describe the default way of
integrating modules via events, Spring Modulith provides
@ApplicationModuleListener to shortcut the declaration

An application module listener

```java

@Component
class InventoryManagement {

    @ApplicationModuleListener
    void on(OrderCompleted event) { /* … */ }
}
```

## Replace @EventListener with @ApplicationModuleListener

1. Go to `NotificacionService` and replace `@EventListener` for
   `@ApplicationModuleListener`

## Testing your module events with Spring Modulith test Scenario

1. Go to `NotificationServiceTest` and replace the content of the file with
   this one that uses `Modulith test Scenario`

```java
package com.acme.conferencesystem.notifications;

import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.cfp.Proposal;
import com.acme.conferencesystem.cfp.ProposalAcceptedEvent;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.ApplicationModuleTest.BootstrapMode;
import org.springframework.modulith.test.Scenario;

@ApplicationModuleTest(value = BootstrapMode.STANDALONE)
@Import(ContainerConfig.class)
class NotificationServiceTest {

    @Test
    void receiveProposalAcceptedEvent(Scenario scenario) {
        var proposal = Instancio.create(Proposal.class);

        scenario.publish(new ProposalAcceptedEvent(proposal))
                .andWaitForEventOfType(ProposalAcceptedEvent.class)
                .toArrive();
    }

}

```

