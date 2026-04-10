package com.example.distribution_sales_techfira.util;

import java.time.LocalDate;

public class DateUtil {
    public static int getCurrentYear() {
        return LocalDate.now().getYear();
    }
}
