package com.acme.conferencesystem.users.http;

import com.acme.conferencesystem.AbstractIntegrationTest;
import com.acme.conferencesystem.users.business.User;
import com.acme.conferencesystem.users.business.UserRole;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class UserControllerTest extends AbstractIntegrationTest {

    public static final String JOHN_DOE = "John Doe";
    public static final String EMAIL = "john@example.com";
    public static final String PHONE = "(555) 555-5551";

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
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", equalTo(newUserId))
                .body("[0].name", equalTo(JOHN_DOE))
                .body("[0].email", equalTo(EMAIL))
                .body("[0].phone", equalTo(PHONE))
                .body("[0].role", equalTo("SPEAKER"));

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
                .body("role", equalTo("SPEAKER"));
    }

}