package com.acme.conferencesystem;

import org.jmolecules.architecture.layered.ApplicationLayer;
import org.springframework.boot.SpringApplication;
import org.springframework.modulith.Modulith;

@ApplicationLayer
@Modulith
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
