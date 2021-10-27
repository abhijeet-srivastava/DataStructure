package com.walmart;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LeetCodeApril {
    public static void main(String[] args) {
        LeetCodeApril lca = new LeetCodeApril();
        //lca.testConstructArray();
        //lca.testValid();
        //lca.testCritical();
        lca.testSorting();
    }

    private void testSorting() {
        char[] tasks = {'a', 'c', 'b', 'b','c','a','a', 'b','c','c','c','a','b','b', 'a','c'};
        Map<Character, Long> res = leastInterval(tasks, 2);
        Consumer<Map.Entry<Character, Long>> consumer = (entry) -> String.format("%c - %d", entry.getKey(), entry.getValue());
        res.entrySet().forEach((entry) -> System.out.printf(String.format("%c - %d\n", entry.getKey(), entry.getValue())));
    }

    public  Map<Character, Long> leastInterval(char[] tasks, int n) {
        Stream<Character> charStream = IntStream.range(0, tasks.length)
                .mapToObj(idx -> tasks[idx]);
        Map<Character, Long> freqMap = IntStream.range(0, tasks.length).mapToObj(idx -> tasks[idx]).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Character, Long> sortedByValue = freqMap.entrySet().stream().sorted(
                Map.Entry.<Character,Long>comparingByValue().reversed()
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedByValue;
    }
    private void testCritical() {
        List<List<Integer>> connections = Arrays.asList(
                Arrays.asList(0,1),
                Arrays.asList(1,2),
                Arrays.asList(2, 0),
                Arrays.asList(1,3)
        );
        List<List<Integer>> ccs = criticalConnections(4, connections);
        for(List<Integer> cc : ccs) {
            System.out.printf("%d <-> %d\n", cc.get(0), cc.get(1));
        }
    }

    private void testValid() {
        System.out.printf("%b \n", isNumber("-1."));
    }

    private void testConstructArray() {
        int[] arr = constructArray(6, 3);
        System.out.printf("%s \n", IntStream.of(arr)
                .mapToObj(Integer::valueOf)
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }

    public boolean isNumber(String s) {
        String[] split = s.split("[eE]");
        boolean isValid =true;
        if(s == null || s.isEmpty()
                || s.charAt(0) == 'e' || s.charAt(0) == 'E'
                || s.charAt(s.length()-1) == 'e' || s.charAt(s.length()-1) == 'E') {
            isValid = false;
        } else if(split.length == 1) {
            isValid =  isValidDouble(split[0]);
        } else if(split.length == 2) {
            isValid = !split[0].trim().isEmpty()
                    && isValidDouble(split[0])
                    && !split[1].trim().isEmpty()
                    && isValidInteger(split[1]);
        } else {
            isValid = false;
        }
        return isValid;
    }

    private boolean isValidDouble(String s) {
        boolean isValid = true;
        try {
            isValid = Double.valueOf(s)  < Double.MAX_VALUE && Double.valueOf(s) > (-1  * Double.MIN_VALUE);
        } catch(NumberFormatException nfe) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isValidInteger(String s) {
        boolean isValid = true;
        try {
            isValid = Integer.valueOf(s) < Integer.MAX_VALUE && Double.valueOf(s) > Integer.MIN_VALUE;
        } catch(NumberFormatException nfe) {
            isValid = false;
        }
        return isValid;
    }
    public int[] constructArray(int n, int k) {
        //For k constructions need, k+1 elements
        //1,  n, 2, n-1, 3, n-2
        //Rest n-k-1 elements for 1 constructions= Increasing order
        int[] arr = new int[n];

        int index = 0;
        for(int i = 1;  i <= k+1; i++) {
            arr[index++] = ((i%2) == 0 ? k+1 - i : i);
        }
        for(int i = n-k-1; i < n-1; i++) {
            arr[i] = i  + (n-k-1);
            arr[i+1] = (n - i + 1) + (n-k-1);
            //
        }
        return arr;
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer>[] graph = new List[n];
        for(int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for(List<Integer> connection : connections) {
            graph[connection.get(0)].add(connection.get(1));
            graph[connection.get(1)].add(connection.get(0));
        }
        int[] discoverTime = new int[n];
        int[] lowTime = new int[n];
        int[] time = {0};
        Arrays.fill(discoverTime, -1);
        for(int i = 0; i < n; i++) {
            if(discoverTime[i] == -1) {
                dfs(i, time, discoverTime, lowTime, result, graph, i);
            }
        }
        return result;
    }

    private void dfs(int current, int[] time, int[] discoverTime, int[] lowTime, List<List<Integer>> result, List<Integer>[] graph, int parent) {
        discoverTime[current] = lowTime[current] = time[0];
        time[0] += 1;
        for(int neighbour : graph[current]) {
            if(neighbour == parent) {
                continue;
            } else if(discoverTime[neighbour] == -1) {
                //Unvisited
                dfs(neighbour, time, discoverTime, lowTime, result,graph, current);
                lowTime[current] = Math.min(lowTime[current], lowTime[neighbour]);
                //Check if there is back edge from neighbour to any ancestor of current
                if(lowTime[neighbour] > discoverTime[current]) {
                    result.add(Arrays.asList(current, neighbour));
                }
            } else {
                //Already visited node
                lowTime[current] = Math.min(lowTime[current], discoverTime[neighbour]);
            }
        }
    }
}
