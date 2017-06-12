package com.hiti.utility.time;

import java.util.Calendar;
import java.util.Locale;

public class SchedualCheck {
    public static boolean IsDueTime(String strAvTime) {
        int year = Integer.parseInt(strAvTime.substring(0, 4));
        int month = Integer.parseInt(strAvTime.substring(4, 6));
        int day = Integer.parseInt(strAvTime.substring(6, 8));
        Calendar avTime = Calendar.getInstance(Locale.US);
        avTime.set(year, month - 1, day);
        return Calendar.getInstance(Locale.US).after(avTime);
    }
}
