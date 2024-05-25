# Maven Reference

## Offline mode

To run the workshop in offline mode, use the following command:

```shell
# download the dependencies into your local maven repository i.e. `~/.m2/repository
mvn dependency:go-offline
```

```shell
# run verify in offline mode
mvn --offline verify 
```
