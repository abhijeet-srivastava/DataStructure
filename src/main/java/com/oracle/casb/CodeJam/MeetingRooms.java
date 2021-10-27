package com.oracle.casb.CodeJam;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created By : abhijsri
 * Date  : 11/09/18
 **/
public class MeetingRooms {

    public static void main(String[] args) {
        MeetingRooms meetingRooms = new MeetingRooms();
        //System.out.println(4 >> 2);
        //meetingRooms.testWordFound();
        //meetingRooms.testMinRoomsReq();
        Map<Integer, List<Integer>> map = new TreeMap<>();
        map.forEach((k,v) -> Collections.sort(v));
    }

    private void testWordFound() {
        List<String> wordDict = Arrays.asList("apple", "pen", "applepen", "pine", "pineapple");
        List<String> res = wordBreak("pineapplepenapple", wordDict);
        //res.get(0).charAt()
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> result = new ArrayList<>();
        List<String> path = new ArrayList<>();
        //Character.isLetter()
        return result;
    }
    private int findIndex(String str, String[] dict, boolean[] visited) {
        for(int i = 0; i < dict.length; i++) {
            if(dict[i].equals(str) && !visited[i]) {
                return i;
            }
        }
        return -1;
    }

    private void testMinRoomsReq() {
        int[][] intervals = {{0,10}, {3,7}, {5,10}, {8,20}, {10,15}, {7,8}};
        int rooms = getMinRooms(intervals);
        int rooms1 = getMinRooms1(intervals);
        int room2 = getMinRooms3(intervals);
        System.out.println("Total rooms " + rooms);
    }

    private int getMinRooms3(int[][] intervals) {
        TimePoint[] timePoints = new TimePoint[intervals.length * 2];
        for (int i = 0; i < intervals.length; i++) {
            timePoints[2*i] = new TimePoint(intervals[i][0], true);
            timePoints[2*i+1] = new TimePoint(intervals[i][1], false);
        }
        Arrays.sort(timePoints, (a,b) -> compare(a,b));
        int count = 0;
        int max = 0;
        for (TimePoint t : timePoints) {
            if (t.isStart) {
                count += 1;
            } else {
                count -= 1;
            }
            if (max < count) {
                max = count;
            }

        }
        return max;
    }

    private int compare(TimePoint a, TimePoint b) {
        return a.value == b.value ? (a.isStart ? 1 : -1)  : (a.value - b.value);
    }

    private int getMinRooms1(int[][] intervals) {
        int[] start = new int[intervals.length];
        int[] end = new int[intervals.length];
        int index = 0;
        for (int[] interval : intervals) {
            start[index] = interval[0];
            end[index++] = interval[1];
        }
        Arrays.sort(start);
        Arrays.sort(end);
        return 0;
    }

    private int getMinRooms(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> arranger = new PriorityQueue<>(intervals.length, (a,b) -> a-b);
        arranger.add(intervals[0][1]);
        int size = 1;
        for (int i = 1; i < intervals.length; i++) {
            if (arranger.peek() <= intervals[i][0]) {
                arranger.remove();
            }
            arranger.add(intervals[i][1]);
            if (arranger.size() > size) {
                size = arranger.size();
            }
        }
        return arranger.size();
    }
    class TimePoint {
        int value;
        boolean isStart;

        public TimePoint(int value, boolean isStart) {
            this.value = value;
            this.isStart = isStart;
        }
    }
    class Interval {
        private int start;
        private int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
        public int getDuration() {
            return end - start + 1;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }

    public int leastInterval(char[] tasks, int n) {
        if(n == 0) {
            return tasks.length;
        }
        int[] arr = new int[26];
        for(char task : tasks) {
            arr[task-'a'] += 1;
        }
        Integer[] frequency = Arrays.stream(arr).boxed().filter((a) -> a > 0).sorted().toArray(Integer[]::new);
        int time = 0;
        int index = 0;
        while (index < frequency.length) {

        }
        return -1;
    }

    public int getCombinations(int n, int r) {
        //4c1 = 4c3
        if(r == 0) {
            return 1;
        } else if(r == 1) {
            return n;
        }
        if(r > n/2) {
            r = n-r;
        }
        java.math.BigInteger result = java.math.BigInteger.ONE;
        java.math.BigInteger den = java.math.BigInteger.ONE;
        java.math.BigInteger num = java.math.BigInteger.valueOf(n);

        //int result = 1;
        //int den = 1;
        for(int i = 0; i < r; i++) {
            result = result.multiply(num);
            num = num.subtract(java.math.BigInteger.ONE);
            result = result.divide(den);
            den = den.add(java.math.BigInteger.ONE);
        }
        return result.intValue();
    }
}
