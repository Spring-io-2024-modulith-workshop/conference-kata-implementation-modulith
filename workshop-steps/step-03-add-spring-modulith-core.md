# Step 3: Add Spring Modulith Bom and Core Dependencies

## Replace @SpringBootApplication with @Modulith

Go to Application.java and replace `@SpringBootApplication` with `@Modulith`
to tell spring modulith that this is a modular application.

## Add Bom and core dependency

Add the following to the `pom.xml` file:

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

## Spring Modulith documentation

For further information, you can visit the spring-modulith reference.

[https://docs.spring.io/spring-modulith/reference/](https://docs.spring.io/spring-modulith/reference/)

[https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

[https://github.com/spring-projects/spring-modulith](https://github.com/spring-projects/spring-modulith)
