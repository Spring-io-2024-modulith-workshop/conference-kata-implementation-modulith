package com.acme.conferencesystem.users;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.UUID;

public class UsersTestAPI {

    public static UUID createSpeaker(RequestSpecification requestSpecification) {
        String speakerIdString = given(requestSpecification)
                .contentType(ContentType.JSON)
                .body("""
                        {
                             "name": "John Doe",
                             "email": "john@example.com",
                             "phone": "(555) 555-5551",
                             "role": "SPEAKER"
                         }""")
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        return UUID.fromString(speakerIdString);
    }
}
