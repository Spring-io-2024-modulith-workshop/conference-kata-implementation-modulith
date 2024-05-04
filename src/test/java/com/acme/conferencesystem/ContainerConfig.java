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
                .withCopyToContainer(MountableFile.forHostPath("db/migration/v1__create_proposals_table.sql", 777), "/docker-entrypoint-initdb.d/V1.sql")
                .withCopyToContainer(MountableFile.forHostPath("db/migration/v2__create_users_table.sql", 777), "/docker-entrypoint-initdb.d/V2.sql")
                .withCopyToContainer(MountableFile.forHostPath("db/migration/v3__create_tickets_table.sql", 777), "/docker-entrypoint-initdb.d/V3.sql")
                .withCopyToContainer(MountableFile.forHostPath("db/migration/v4__create_votes_table.sql", 777), "/docker-entrypoint-initdb.d/V4.sql")
                ;
    }

}
