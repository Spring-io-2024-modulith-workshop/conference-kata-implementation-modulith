package com.acme.conferencesystem.voting.persistence;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
public class ContainerVoteTestConfig {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:16-alpine")
                .withCopyToContainer(MountableFile.forClasspathResource("sql/schema/2-users.sql", 777), "/docker-entrypoint-initdb.d/2-users.sql")
                .withCopyToContainer(MountableFile.forClasspathResource("sql/schema/3-tickets.sql", 777), "/docker-entrypoint-initdb.d/3-tickets.sql")
                .withCopyToContainer(MountableFile.forClasspathResource("sql/schema/1-proposals.sql", 777), "/docker-entrypoint-initdb.d/1-proposals.sql")
                .withCopyToContainer(MountableFile.forClasspathResource("sql/schema/4-votes.sql", 777), "/docker-entrypoint-initdb.d/4-votes.sql")
                .withCopyToContainer(MountableFile.forClasspathResource("sql/schema/5-vote-proposal-scenario.sql", 777), "/docker-entrypoint-initdb.d/5-vote-proposal-scenario.sql")
                ;
    }

}
