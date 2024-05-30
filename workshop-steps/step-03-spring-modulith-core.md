# Step 3: Add Spring Modulith Bom and Core Dependencies

## Replace @SpringBootApplication with @Modulith

- [ ] In
  the [`Application.java`](../src/main/java/com/acme/conferencesystem/Application.java)
  class, replace `@SpringBootApplication`
  with `@Modulith` to tell spring modulith that this is a modular application.

```java

@Modulith
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
```

## Add Bom and core dependency

- [ ] Add the following dependencies to the [`pom.xml`](../pom.xml) file:

```xml

<properties>
  <spring-modulith.version>1.2.0</spring-modulith.version>
</properties>

<dependencies>
<!-- Spring Modulith -->
<dependency>
  <artifactId>spring-modulith-starter-core</artifactId>
  <groupId>org.springframework.modulith</groupId>
</dependency>
</dependencies>

<dependencyManagement>
<dependencies>
  <dependency>
    <artifactId>spring-modulith-bom</artifactId>
    <groupId>org.springframework.modulith</groupId>
    <scope>import</scope>
    <type>pom</type>
    <version>${spring-modulith.version}</version>
  </dependency>
</dependencies>
</dependencyManagement>
```
