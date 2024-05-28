# Step 6 fixing dependencies

high-level modules should depend on abstractions rather than concrete
implementations.

## Modulith core Violations

Run your tests. You will start to see the module dependencies
violations in the output.

```text
org.springframework.modulith.core.Violations: - Module 'voting' depends on non-exposed type com.acme.conferencesystem.cfp.proposals.business.ProposalService within module 'cfp'!
```
