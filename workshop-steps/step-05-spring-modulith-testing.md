# Step 5: Start testing your modules in isolation

## Add the dependency to be able to use @ApplicationModuleTest with @Modulith in your pom.xml

```xml

<dependency>
  <artifactId>spring-modulith-starter-test</artifactId>
  <groupId>org.springframework.modulith</groupId>
  <scope>test</scope>
</dependency>
```

## Replace @SpringBootTest with @ApplicationModuleTest

As this is not production, is a safe environment, let's replace all
`@SpringBootTest` with `@ApplicationModuleTest`, please go one by one because if
you replace all automatically, you will replace also `SpringBootTest.
WebEnvironment.RANDOM_PORT`, and we still need that.

In a more agile environment, you can go module by module changing the
annotation and executing the tests annotated with `@ApplicationModuleTest`

This annotation by default uses **STANDALONE mode** to indicate spring
modulith to only load the beans of the
current module.
To leave you test your module in isolation.

In case your module uses other modules, you can fine-tune that with
**DIRECT_DEPENDENCIES**, or **ALL_DEPENDENCIES** (but always try to be as
narrow as possible, testing your modules).

```java
/**
 * STANDALONE(DependencyDepth.NONE),
 * DIRECT_DEPENDENCIES(DependencyDepth.IMMEDIATE),
 * ALL_DEPENDENCIES(DependencyDepth.ALL);
 */

@ApplicationModuleTest(
        mode = BootstrapMode.ALL_DEPENDENCIES,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

```

in case you're using classes in your SpringBootTest, you will need to use
@Import instead because this option is not supported in
@ApplicationModuleTests annotation

```java
@SpringBootTest(webEnvironment = SpringBootTest.
        WebEnvironment.RANDOM_PORT, classes = {ContainerConfig.class})
```

to

```java
@ApplicationModuleTest(
        mode = BootstrapMode.ALL_DEPENDENCIES,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Import(ContainerConfig.class

```

When you don't have more compilation errors.
Go to [step 06](step-06-spring-modulith-fixing-dependencies.md)
for continuing fixing your tests,
where we will be running the
test one by one and fixing module dependencies.
