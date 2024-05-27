# Step 3: Add Spring Modulith Bom and Core Dependencies

## Replace @SpringBootTest with @ApplicationModuleTest

## Add the dependency to be able to use @ApplicationModuleTest with @Modulith in your pom.xml

```xml

<dependency>
  <artifactId>spring-modulith-starter-test</artifactId>
  <groupId>org.springframework.modulith</groupId>
  <scope>test</scope>
</dependency>
```

## Modulith core Violations

Run your tests. You will start to see the module dependencies
violations in the output.

```text
org.springframework.modulith.core.Violations: - Module 'voting' depends on non-exposed type com.acme.conferencesystem.cfp.proposals.business.ProposalService within module 'cfp'!
```
