package com.acme.conferencesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

class TestApplication {

    public static void main(String[] args) {
        SpringApplication.
                from(Application::main)
                .with(LocalContainerConfig.class)
                .run(args);
    }

    @TestConfiguration(proxyBeanMethods = false)
    static class LocalContainerConfig {

        @Bean
        @ServiceConnection
        @RestartScope
        PostgreSQLContainer<?> postgreSQLContainer() {
            return new PostgreSQLContainer<>("postgres:16-alpine");
        }
    }

}
