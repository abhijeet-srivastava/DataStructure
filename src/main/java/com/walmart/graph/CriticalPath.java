package com.walmart.graph;

import java.util.*;
import java.util.stream.Collectors;

public class CriticalPath {

    public static void main(String[] args) {
        CriticalPath cp = new CriticalPath();
        cp.testTarzanScc();
    }

    private void testTarzanScc() {
        int n = 8;
//        Queue<String> queue = new LinkedList<String>();
//        queue.add("start");
//        queue.offer()
        List<Integer>[] graph = createGraph(n);
        /*addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 2, 0);
        addEdge(graph, 3, 4);
        addEdge(graph, 3, 7);
        addEdge(graph, 4, 5);
        addEdge(graph, 5, 6);
        addEdge(graph, 5, 0);
        addEdge(graph, 6, 0);
        addEdge(graph, 6, 2);
        addEdge(graph, 6, 4);
        addEdge(graph, 7, 3);
        addEdge(graph, 7, 5);*/
        addEdge(graph, 6, 0);
        addEdge(graph, 6, 2);
        addEdge(graph, 3, 4);
        addEdge(graph, 6, 4);
        addEdge(graph, 2, 0);
        addEdge(graph, 0, 1);
        addEdge(graph, 4, 5);
        addEdge(graph, 5, 6);
        addEdge(graph, 3, 7);
        addEdge(graph, 7, 5);
        addEdge(graph, 1, 2);
        addEdge(graph, 7, 3);
        addEdge(graph, 5, 0);

        Map<Integer, List<Integer>> result = tarjanScc(n,graph);

        System.out.printf("Number of Strongly Connected Components: %d\n", result.size());
        for (Map.Entry<Integer, List<Integer>> entry : result.entrySet()) {
            System.out.printf("SSC[%d] : %s\n", entry.getKey(),
                    entry.getValue()
                            .stream()
                            .map(String::valueOf).collect(Collectors.joining("," , "[", "]"))
            );
        }

    }
    public static List<Integer>[] createGraph(int n) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) //graph.add(new ArrayList<>());
            graph[i] = new ArrayList<>();
        return graph;
    }
    public static void addEdge(List<Integer>[] graph, int from, int to) {
        /*if(graph.get(from) == null) {
            graph.add(from, new ArrayList<>());
        }*/
        graph[from].add(to);
    }

    public Map<Integer, List<Integer>> tarjanScc(int n, List<Integer>[] graph) {
        int[] id = {0};
        boolean[] visited = new boolean[n];
        int[] ids = new int[n];
        int[] low = new int[n];
        Arrays.fill(ids, -1);
        Deque<Integer> stack = new ArrayDeque<Integer>();

        for(int i = 0; i < n; i++) {
            if(ids[i] ==  -1) {
                dfsTarzan(i, id, ids, low, visited, graph, stack);
            }
        }
        Map<Integer, List<Integer>> result = new HashMap<>();
        for (int i =0; i < n; i++) {
            //result.getOrDefault(low[i], new ArrayList<>()).add(i);
            if(!result.containsKey(low[i])) {
                result.put(low[i], new ArrayList<>());
            }
            result.get(low[i]).add(i);
        }
        return result;
    }

    private void dfsTarzan(int current, int[] id, int[] ids, int[] low, boolean[] visited, List<Integer>[] graph, Deque<Integer> stack) {
        ids[current] = low[current] = id[0];
        id[0] += 1;
        visited[current] = true;
        stack.push(current);

        for(int neighbour : graph[current]) {
            if(ids[neighbour] == -1) {
                //Unvisited
                dfsTarzan(neighbour, id, ids, low, visited,graph, stack);
            }
            //Check if current is on stack
            if(visited[neighbour]) {
                low[current]  = Math.min(low[current], low[neighbour]);

            }
        }
        if(ids[current] == low[current]) {
            //Start of SCC
            int node = stack.pop();
            visited[node] = false;
            while (node != current)  {
                node = stack.pop();
                visited[node] = false;
            }
            visited[node] = false;
        }
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
