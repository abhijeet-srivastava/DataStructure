package com.oracle.casb;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestEnumAll {

    private static Map<Integer, ZonedDateTime> YEAR_FIRST_WMWEEK_DAY = new HashMap<>();

    public enum Regions {
        ALL(-1, "ALL"),
        CENTRAL(1, "CENTRAL"),
        NORTH_EAST(2, "NORTH EAST"),
        SOUTH_EAST(3, "SOUTH EAST"),
        WEST(4, "WEST");


        private int id;
        private String name;

        Regions(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        private static Map<String, Regions> regionsMap
                = ImmutableMap.<String, Regions>of(
                "ALL", ALL,
                "CENTRAL", CENTRAL,
                "NORTH EAST", NORTH_EAST,
                "SOUTH EAST", SOUTH_EAST,
                "WEST", WEST
        );

        public static Regions getRegionByName(String regionName) {
            return regionsMap.get(regionName.toUpperCase());
        }

        public static Regions forValue(String value) {
            return getRegionByName(value);
        }
    }

    public static void main(String[] args) {
        /*Set<Regions> paramList = ImmutableSet.of(Regions.ALL, Regions.CENTRAL);
        System.out.printf("Contains All %b%c", isFilterAll(paramList), '\n');*/
        /*String[] split = "e9".split("e");
        System.out.println(split[0].isEmpty());
        System.out.printf("%d", split.length );*/
        TestEnumAll tea = new TestEnumAll();
        tea.testEnumOrder();

        //System.out.println(tea.isNumber("4e1.e"));
        //System.out.printf("Walmart week %d", getWmWeekNumFromDate(ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())));
    }

    private void testEnumOrder() {
        Regions[] arr = Regions.values();
        for(int i = 0; i < arr.length; i++) {
            System.out.printf("#%d : %s\n", i+1, arr[i].name);
        }
    }

    public boolean isNumber(String s) {
        if (s.indexOf('e') == 0 || s.lastIndexOf('e') == s.length() - 1) {
            return false;
        }
        String[] split = s.split("e");
        int l = split.length;
        if(l == 1) {
            return isNumeric1(s);
        } else if(l == 2) {
            return isNumeric1(split[0]) && isNumeric1(split[1]);
        } else {
            return false;
        }
    }

    private boolean isNumeric1(String s) {
        boolean result = true;
        try{
            Double l = Double.valueOf(s.trim());
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    private boolean isNumeric(String s) {
        boolean result = !s.isEmpty();
        char[] arr = s.toCharArray();
        for(int i = 0; i < arr.length; i++) {
            if(!Character.isDigit(arr[i])) {
                if ((arr[i] != '-' && arr[i] != '+') || i != 0) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }
    public static boolean isFilterAll(Set<Regions> regionList) {
        return regionList == null
                || regionList.isEmpty()
                || regionList.contains(-1);
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

    private static ZonedDateTime getFirstWmWeekDayForYear(int targetYear) {
        ZonedDateTime firstJan = LocalDate.of(targetYear, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime lastSatInJan  = firstJan.with(TemporalAdjusters.lastInMonth(DayOfWeek.SATURDAY));
        /**
         * If last Saturday falls on 25, Entire week is wrapped in January Only
         * First day of Walmart first week is Feb - 1
         */
        if(lastSatInJan.getDayOfYear() == 25) {
            lastSatInJan = LocalDate.of(targetYear, Month.FEBRUARY, 1).atStartOfDay(ZoneId.systemDefault());
        }
        return lastSatInJan;
    }
}
