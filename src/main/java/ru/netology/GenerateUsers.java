package ru.netology;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class GenerateUsers {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll(RegistrationData registrationData) {
        given()
                .spec(requestSpec)
                .body(registrationData)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationData generateValidActiveUser() {
        Faker faker = new Faker(new Locale("en"));
        String name = faker.name().username().toLowerCase();
        String password = faker.internet().password();
        RegistrationData valid = new RegistrationData(name, password, "active");
        setUpAll(valid);
        return valid;
    }

    public static RegistrationData generateValidBlockedUser() {
        Faker faker = new Faker(new Locale("en"));
        RegistrationData valid = new RegistrationData(
                faker.name().username().toLowerCase(),
                faker.internet().password(),
                "blocked");
        setUpAll(valid);
        return valid;
    }
}
