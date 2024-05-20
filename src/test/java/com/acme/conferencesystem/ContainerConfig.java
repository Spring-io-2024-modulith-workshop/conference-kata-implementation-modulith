package com.acme.conferencesystem;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainerConfig {

    private static PostgreSQLContainer<?> singletonPostgreSQLContainer;
    private static DataSource singletonDataSource;

    @Bean
    //Alternative to @DynamicPropertySource
    @ServiceConnection
    PostgreSQLContainer<?> postgreSQLContainer() {
        if (singletonPostgreSQLContainer == null) {
            singletonPostgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine");
        }
        return singletonPostgreSQLContainer;
    }

    //Avoids datasource recreation
    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        if (singletonDataSource == null) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName("org.postgresql.Driver");
            hikariConfig.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
            hikariConfig.setUsername(postgreSQLContainer.getUsername());
            hikariConfig.setPassword(postgreSQLContainer.getPassword());
            hikariConfig.setMaximumPoolSize(1);
            singletonDataSource = new HikariDataSource(hikariConfig);
        }
        return singletonDataSource;
    }

}
