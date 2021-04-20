package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;


public class AuthTest {

    @Test
    void shouldAuthActiveUserValid() {
        RegistrationData user = GenerateUsers.generateValidActiveUser();
        open("http://localhost:9999");
        $("[name='login']").setValue(user.getLogin());
        $("[name='password']").setValue(user.getPassword());
        $(".button").click();
        $(byText("Личный кабинет")).shouldBe(Condition.visible, ofSeconds(10));
    }

    @Test
    void shouldAuthActiveUserInvalidPass() {
        RegistrationData user = GenerateUsers.generateValidActiveUser();
        open("http://localhost:9999");
        $("[name='login']").setValue(user.getLogin());
        $("[name='password']").setValue("psss");
        $(".button").click();
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible, ofSeconds(10));
    }

    @Test
    void shouldAuthActiveUserInvalidLogin() {
        RegistrationData user = GenerateUsers.generateValidActiveUser();
        open("http://localhost:9999");
        $("[name='login']").setValue("Log");
        $("[name='password']").setValue(user.getPassword());
        $(".button").click();
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible, ofSeconds(10));
    }

    @Test
    void shouldAuthActiveUserValidBlock() {
        RegistrationData user = GenerateUsers.generateValidBlockedUser();
        open("http://localhost:9999");
        $("[name='login']").setValue(user.getLogin());
        $("[name='password']").setValue(user.getPassword());
        $(".button").click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible, ofSeconds(10));
    }
}
