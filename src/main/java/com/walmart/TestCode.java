package com.walmart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.time.DayOfWeek;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestCode {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static void main(String[] args) {
        TestCode tc = new TestCode();
        //tc.testGetLastDay();
        tc.testSubtractDays();
    }

    private void testSubtractDays() {
        String currDate = "2021-10-04";
        System.out.printf(String.format("Start date: %s  End date: %s", subtractDays(currDate, 200), currDate));
    }

    private String subtractDays(String endDate, int days) {
        LocalDate ld = LocalDate.parse(endDate, DATE_FORMATTER);
        LocalDate pastDate = ld.minusDays(Long.valueOf(days).longValue());
        return pastDate.format(DATE_FORMATTER);
    }

    private void testGetLastDay() {
        SimpleDateFormat df =   new SimpleDateFormat("MM-dd-yyyy");
        Date date = new Date();
        try {
            date = df.parse("06-30-2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.printf("%s \n", df.format(date));
        Date lastSat = getDateLastDayOfWeek(date, DayOfWeek.SATURDAY);
        System.out.printf("%s \n", df.format(lastSat));
    }

    private Date getDateLastDayOfWeek(Date date, DayOfWeek saturday) {
        ZonedDateTime zonedDateTime
                = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ZonedDateTime nextSat = zonedDateTime.with(ChronoField.DAY_OF_WEEK, 6);
        if(zonedDateTime.compareTo(nextSat) != 0) {
            nextSat = nextSat.plusDays(-7);
        }
        return Date.from(nextSat.toInstant());
    }
    //https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/
    public int minTaps(int n, int[] ranges) {
        int len = ranges.length;
        int[] dp = new int[len];
        Arrays.fill(dp, len + 1);
        dp[0] = 0;

        for (int i = 0; i < len; i++) {
            int start = Math.max(i - ranges[i], 0);
            int end = Math.min(i + ranges[i], len - 1);
            for (int j = start; j <= end; j++) {
                dp[j] = Math.min(dp[j], dp[start] + 1);
                System.out.printf("For %d Tap: dp[%d] = %d\n ", i, j, dp[j]);
            }
        }

        return dp[len - 1] == len + 1 ? -1 : dp[len - 1];
    }

    //https://leetcode.com/problems/jump-game-ii/
    public int jump(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        Arrays.fill(dp, len + 1);
        dp[0] = 0;

        for (int i = 0; i < len; i++) {
            int start = i;
            int end = Math.min(i + nums[i], len - 1);
            for (int j = start; j <= end; j++) {
                dp[j] = Math.min(dp[j], dp[start] + 1);
            }
        }

        return dp[len - 1] == len + 1 ? -1 : dp[len - 1];
    }
    //https://leetcode.com/problems/video-stitching/
    public int videoStitching(int[][] clips, int T) {
        int[] dp = new int[T + 1];
        Arrays.fill(dp, T + 2);
        dp[0] = 0;

        for (int i = 0; i <= T; i++) {
            for (int[] clip : clips) {
                int start = clip[0];
                int end = clip[1];
                if (i >= start && i <= end)
                    dp[i] = Math.min(dp[i], dp[start] + 1);
            }
        }

        return dp[T] == T + 2 ? -1 : dp[T];
    }

    /*CompletableFuture<Integer> fetchAndSum(String...urls) {
        var sum = CompletableFuture.completedFuture(0);
        for(var url : urls) {
            var prev = sum;
            sum = httpClient
                    .sendAsync(request(url), BodyHandlers.ofString()) // 1 - Fetch the url
                    .thenApply(HttpResponse::body)                    // 2 - Read the body
                    .thenApply(String::length)                        // 3 – Get body’s length
                    .thenCombine(sum, (length, val) -> {              // 4 - Sum lengths
                        return val + length;
                    });
        }
        return sum;
    }
    CompletableFuture<Integer> fetchAndSum(String...urls) {
        var sum = CompletableFuture.completedFuture(0);
        return Stream
                .of(urls)
                .peek(url -> out.printf("FETCHING from %s\n", url))
                .map(url -> httpClient
                    .sendAsync(request(url), BodyHandlers.ofString()) // 1 - Fetch the url
                    .thenApply(HttpResponse::body)                    // 2 - Read the body
                    .thenApply(String::length)                        // 3 – Get body’s length
                    .whenComplete((l, err) -> out.printf("=======> from %s\n", url)))
                .reduce(sum, (prev, curr) -> prev
                    .thenCombine(curr, (p, c) -> p + c));             // 4 - Sum lengths
    }
    */
    public int arrangeCoins(int n) {
        return Double.valueOf(Math.ceil(Math.sqrt(2*n + 0.25) - 0.5)).intValue();
    }
}
