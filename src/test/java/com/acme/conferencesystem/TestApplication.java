package com.acme.conferencesystem;

import org.springframework.boot.SpringApplication;

public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.
                from(Application::main)
                .with(ContainerConfig.class)
                .run(args);
    }
}
