# Event Publication Registry

1. Go to your [pom.xml](../pom.xml)

And add the following to your `pom.xml`:

```xml

<dependency>
  <artifactId>spring-modulith-events-api</artifactId>
  <groupId>org.springframework.modulith</groupId>
</dependency>

<dependency>
<artifactId>spring-modulith-starter-jdbc</artifactId>
<groupId>org.springframework.modulith</groupId>
</dependency>
```

2.Go to [application.yml](../src/main/resources/application.yml)

And add the following that will enable the schema initialization for the
`event_publication` record table.

```yaml
  modulith:
    events.jdbc.schema-initialization.enabled: true
```

3. Then open a database client and run the following query:

create a local connection:

```text
username: test
password: test
jdbc:postgresql://localhost:5432/test
```

```sql
select *
from event_publication;
```

4. Run the integration tests is that is empty to see some results.
