package com.habittracker.utils;

import java.time.LocalDate;

public class DateUtils {
    public static boolean isWithinDays(LocalDate date, int days) {
        return date.isAfter(LocalDate.now().minusDays(days));
    }
}
