package com.acme.conferencesystem;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
public class ContainerConfig {

    @Bean
    @ServiceConnection
    @RestartScope
    public PostgreSQLContainer<?> postgreSQLContainer() {

        return new PostgreSQLContainer<>("postgres:16-alpine")
                .withCopyFileToContainer(MountableFile.forClasspathResource("schema.sql"),
                        "/docker-entrypoint-initdb.d/schema.sql");
    }
}