package ru.netology;

import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DatePicker {
    private int randomDate(int min, int max) {
        Random random = new Random();
        int dif = max - min;
        int randomDate = random.nextInt(dif + 1);
        randomDate += min;
        return randomDate;
    }

    void setRandomDateInCalendarByMouseClick(int min, int max) {
        LocalDate date = LocalDate.now();
        int actualMonth = date.getMonthValue();
        int actualYear = date.getYear();
        date = date.plusDays(randomDate(min, max));
        int resultYear = date.getYear();
        int resultMonth = date.getMonthValue();
        int resultDay = date.getDayOfMonth();
        String dayText = Integer.toString(resultDay);

        $(".icon_name_calendar").click();

        while (resultYear > actualYear) {
            $$(".calendar__arrow_direction_right").first().click();
            actualYear++;
        }
        while (resultMonth > actualMonth) {
            $(".calendar__arrow_direction_right:not(.calendar__arrow_double)").click();
            actualMonth++;
        }
        while (actualMonth > resultMonth) {
            $(".calendar__arrow_direction_left:not(.calendar__arrow_double)").click();
            actualMonth--;
        }
        $$("td").find(text(dayText)).click();
    }

    void setRandomDateInCalendarByMouseClick() {
        setRandomDateInCalendarByMouseClick(3, 500);
    }

    void setRandomDateInCalendarByText(int min, int max) {
        String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        date = date.plusDays(randomDate(min, max));
        String dataText = formatter.format(date);
        $("[placeholder='Дата встречи']").setValue(deleteString).setValue(dataText);
    }

    void setRandomDateInCalendarByText() {
        setRandomDateInCalendarByText(3, 500);
    }
}
