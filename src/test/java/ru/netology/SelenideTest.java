package ru.netology;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenideTest {

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
        LocalDate date = LocalDate.now();
        int actualMonth = date.getMonthValue();
        date = date.plusDays(7);
        int resultMonth = date.getMonthValue();
        int resultDay = date.getDayOfMonth();
        String dayText = Integer.toString(resultDay);
        $("[placeholder='Город']").setValue("Са");
        $$(".menu-item__control").find(exactText("Саратов")).click();
        $(".icon_name_calendar").click();
        if (actualMonth != resultMonth) {
            $(".calendar__arrow_direction_right:not(.calendar__arrow_double)").click();
        }
        $$("td").find(text(dayText)).click();
        $("[name='name']").setValue("Ваня Пупкин!");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $("[data-test-id='notification']").shouldHave(visible, Duration.ofSeconds(15));
    }

    @Test
    void failedCity() {
        LocalDate date = LocalDate.now();
        int actualMonth = date.getMonthValue();
        date = date.plusDays(7);
        int resultMonth = date.getMonthValue();
        int resultDay = date.getDayOfMonth();
        String dayText = Integer.toString(resultDay);
        $("[placeholder='Город']").setValue("Алексеевка");
        $(".icon_name_calendar").click();
        if (actualMonth != resultMonth) {
            $(".calendar__arrow_direction_right:not(.calendar__arrow_double)").click();
        }
        $$("td").find(text(dayText)).click();
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
        String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        date = date.plusDays(1);
        String dataText = formatter.format(date);
        $("[placeholder='Город']").setValue("Са");
        $$(".menu-item__control").find(exactText("Саратов")).click();
        $("[placeholder='Дата встречи']").setValue(deleteString).setValue(dataText);
        $("[name='name']").setValue("Ваня Пупкин");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $$("[class='input__sub']").find(exactText("Заказ на выбранную дату невозможен")).shouldHave(visible);
    }

    @Test
    void failedName() {
        LocalDate date = LocalDate.now();
        int actualMonth = date.getMonthValue();
        date = date.plusDays(7);
        int resultMonth = date.getMonthValue();
        int resultDay = date.getDayOfMonth();
        String dayText = Integer.toString(resultDay);
        $("[placeholder='Город']").setValue("Са");
        $$(".menu-item__control").find(exactText("Саратов")).click();
        $(".icon_name_calendar").click();
        if (actualMonth != resultMonth) {
            $(".calendar__arrow_direction_right:not(.calendar__arrow_double)").click();
        }
        $$("td").find(text(dayText)).click();
        $("[name='name']").setValue("Ваня Пупкин!");
        $("[name='phone']").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("[class='button__text']").click();
        $$("[class='input__sub']").find(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldHave(visible);
    }

    @Test
    void failedCheckbox() {
        LocalDate date = LocalDate.now();
        int actualMonth = date.getMonthValue();
        date = date.plusDays(7);
        int resultMonth = date.getMonthValue();
        int resultDay = date.getDayOfMonth();
        String dayText = Integer.toString(resultDay);
        $("[placeholder='Город']").setValue("Са");
        $$(".menu-item__control").find(exactText("Саратов")).click();
        $(".icon_name_calendar").click();
        if (actualMonth != resultMonth) {
            $(".calendar__arrow_direction_right:not(.calendar__arrow_double)").click();
        }
        $$("td").find(text(dayText)).click();
        $("[name='name']").setValue("Ваня Пупкин");
        $("[name='phone']").setValue("+79999999999");
        $("[class='button__text']").click();
        String actual = $(".input_invalid").getText();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных";
        assertEquals(expected,actual.trim());
    }
}
