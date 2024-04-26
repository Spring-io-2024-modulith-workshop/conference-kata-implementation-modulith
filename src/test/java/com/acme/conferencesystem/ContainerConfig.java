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
                .withCopyToContainer(MountableFile.forClasspathResource("sql/schema/3-tickets.sql"), "/docker-entrypoint-initdb.d/tickets.sql")
                .withCopyToContainer(MountableFile.forClasspathResource("sql/schema/1-proposals.sql"), "/docker-entrypoint-initdb.d/proposals.sql")
                .withCopyToContainer(MountableFile.forClasspathResource("sql/schema/2-users.sql"), "/docker-entrypoint-initdb.d/users.sql");
    }

}
