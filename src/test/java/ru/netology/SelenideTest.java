package ru.netology;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenideTest {
    DatePicker datePicker = new DatePicker();

    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true;
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void passedRegistration() {
        $("[placeholder='Город']").setValue("Са");
        $$(".menu-item__control").find(exactText("Саратов")).click();
        datePicker.setRandomDateInCalendarByMouseClick(3, 3);
        $("[name='name']").setValue("Ваня Пупкин");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $("[data-test-id='notification']").shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    void failedCity() {
        $("[placeholder='Город']").setValue("Алексеевка");
        $(".icon_name_calendar").click();
        datePicker.setRandomDateInCalendarByMouseClick(800, 1000);
        $("[name='name']").setValue("Ваня Пупкин");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        String actual = $("[data-test-id='city'] [class='input__sub']").getText();
        String expected = "Доставка в выбранный город недоступна";
        assertEquals(expected,actual.trim());
    }

    @Test
    void failedData() {
        $("[placeholder='Город']").setValue("Са");
        $$(".menu-item__control").find(exactText("Саратов")).click();
        datePicker.setRandomDateInCalendarByText(1, 2);
        $("[name='name']").setValue("Ваня Пупкин");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $$("[class='input__sub']").find(exactText("Заказ на выбранную дату невозможен")).shouldHave(visible);
    }

    @Test
    void failedName() {
        $("[placeholder='Город']").setValue("Са");
        $$(".menu-item__control").find(exactText("Саратов")).click();
        datePicker.setRandomDateInCalendarByText();
        $("[name='name']").setValue("Ваня Пупкин!");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $$("[class='input__sub']").find(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldHave(visible);
    }

    @Test
    void failedCheckbox() {
        $("[placeholder='Город']").setValue("Са");
        $$(".menu-item__control").find(exactText("Саратов")).click();
        $(".icon_name_calendar").click();
        datePicker.setRandomDateInCalendarByMouseClick();
        $("[name='name']").setValue("Ваня Пупкин");
        $("[name='phone']").setValue("+79999999999");
        $("[class='button__text']").click();
        String actual = $(".input_invalid").getText();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных";
        assertEquals(expected,actual.trim());
    }
}
