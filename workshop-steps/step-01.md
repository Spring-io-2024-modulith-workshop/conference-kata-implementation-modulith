# Step 1: Prepare the environment

## Check if your Docker engine is running

Ensure Docker is up and running on your machine, by following these steps:

1. Open your terminal or command prompt.

2. Run the following command, and check that you get the server engine version:
   (Also you can use just the command `docker version` if you have any issue
   with the --format parameter)

```bash
$ docker version --format '{{.Server.Version}}'
26.1.1
```

In case you don't have Docker installed in your system or any other
docker engine, please do it to be able to run the local environment.

[Docker desktop](https://www.docker.com/products/docker-desktop/)

N.B. If you don't want to use containers, with some modifications on the
project, you could use a local Postgresql database.

## \(optionally\) Download test containers desktop

In this workshop we are using test containers, it's very recommended to
install test containers desktop because it brings with some useful features
like select a container runtime or freeze containers shutdown...

FYI, You need to create a free account to use it.

More info here:
[Test containers desktop docs](https://testcontainers.com/desktop/docs/)

## Download the project

Clone the following project from GitHub to your computer:  
[https://github.com/Spring-io-2024-modulith-workshop/conference-kata-implementation-modulith/tree/workshop](https://github.com/Spring-io-2024-modulith-workshop/conference-kata-implementation-modulith/tree/workshop)

## Download the dependencies in case you can encounter connection issues the day of the workshop

With Maven:

```shell
./mvnw clean compile -DskipTests

or 

./mvnw dependency:go-offline
```

## \(optionally\) Pull the required images before doing the workshop

This might be helpful if the internet connection at the workshop venue is
somewhat slow.

```text
docker pull postgres:16-alpine
```

## This projects runs with Java 21

Be sure that you have Java 21 installed in your system. We personally use
SDKMAN to mana jvm installations for MacOs.
