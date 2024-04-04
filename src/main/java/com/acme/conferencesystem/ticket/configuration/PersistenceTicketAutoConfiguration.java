package com.acme.conferencesystem.ticket.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class PersistenceTicketAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.ticket-datasource")
    public DataSource ticketDataSource() {
        return DataSourceBuilder.create().build();
    }
}
