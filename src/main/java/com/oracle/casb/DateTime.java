package com.oracle.casb;

import java.time.Month;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

public class DateTime {

    private static Map<Integer, ZonedDateTime> YEAR_FIRST_WMWEEK_DAY = new HashMap<>();
    private static  String DATE_FORMAT_FE = "yyyy-MM-dd";

    private static DateTimeFormatter ZONED_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_FE);

    private static ZoneId TIME_ZONE_ID = ZoneId.systemDefault();

    public static void main(String[] args) {
        ZonedDateTime zdt
                = LocalDate.of(2019, Month.JANUARY, 25).atStartOfDay(ZoneId.systemDefault());
        System.out.printf("Date %s weeknum %d\n" , ZONED_TIME_FORMATTER.format(zdt),  getWmWeekNumFromDate(zdt));

    }

    public static int getWmWeekNumFromDate(ZonedDateTime targetDate) {
        int targetYear = targetDate.getYear();
        ZonedDateTime firstWmWeekDayForYear
                = YEAR_FIRST_WMWEEK_DAY.computeIfAbsent(targetYear,
                k -> getFirstWmWeekDayForYear(targetYear));
        if (targetDate.compareTo(firstWmWeekDayForYear) < 0) {
            int prevYear = targetYear - 1;
            firstWmWeekDayForYear
                    = YEAR_FIRST_WMWEEK_DAY.computeIfAbsent(prevYear,
                    k -> getFirstWmWeekDayForYear(prevYear));
        }
        int noOfDaysBetween = Long.valueOf(ChronoUnit.DAYS.between(firstWmWeekDayForYear, targetDate)).intValue();
        int wmWeekNum = (noOfDaysBetween/7) + 1;
        return firstWmWeekDayForYear.getYear() * 100 + wmWeekNum;
    }

    private static ZonedDateTime getFirstWmWeekDayForYear(int targetYear ) {
        ZonedDateTime firstJan = LocalDate.of(targetYear, Month.JANUARY, 1).atStartOfDay(TIME_ZONE_ID);
        ZonedDateTime lastSatInJan = firstJan.with(TemporalAdjusters.lastInMonth(DayOfWeek.SATURDAY));
        /**
         * If last Saturday falls on 25, Entire week is wrapped in January Only
         * First day of Walmart first week is Feb - 1
         */
        if (lastSatInJan.getDayOfYear() == 25) {
            lastSatInJan = LocalDate.of(targetYear, Month.FEBRUARY, 1).atStartOfDay(TIME_ZONE_ID);
        }
        return lastSatInJan;
    }
}
