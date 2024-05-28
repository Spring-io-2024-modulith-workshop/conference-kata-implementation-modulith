package com.acme.conferencesystem.users.http;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.ContainerConfig;
import com.acme.conferencesystem.users.business.User;
import com.acme.conferencesystem.users.business.UserRole;
import com.acme.conferencesystem.users.persistence.UsersRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;


@ApplicationModuleTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContainerConfig.class)
class UserControllerIntegrationTest extends AbstractIntegrationTest {

    public static final String JOHN_DOE = "John Doe";
    public static final String EMAIL = "john@example.com";
    public static final String PHONE = "(555) 555-5551";

    @Autowired
    UsersRepository repository;

    @Test
    void endToEndTest() {
        User user = new User(null, JOHN_DOE, EMAIL, PHONE, UserRole.SPEAKER);

        // Submit user
        var newUserId = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Get all users
        given(requestSpecification)
                .when()
                .get("/users")
                .then()
                .statusCode(200);

        // Get user by ID
        given(requestSpecification)
                .when()
                .get("/users/{id}", newUserId)
                .then()
                .statusCode(200)
                .body("id", equalTo(newUserId))
                .body("name", equalTo(JOHN_DOE))
                .body("email", equalTo(EMAIL))
                .body("phone", equalTo(PHONE))
                .body("role", equalTo(UserRole.SPEAKER.toString()));
    }

}
