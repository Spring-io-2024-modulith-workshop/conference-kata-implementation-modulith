package com.acme.conferencesystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainerConfig.class)
class ApplicationTests extends AbstractIntegrationTest {

    @Test
    void test() {
    }

}
