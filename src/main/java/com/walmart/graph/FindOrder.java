package com.walmart.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindOrder {

    public static void main(String[] args) {
        FindOrder graphOrder = new FindOrder();
        graphOrder.testSimple();
    }

    private void testSimple() {
        int[][] prerequisites = {{1,  0}};
        int[] order = findOrder(2, prerequisites);
        System.out.printf("%s\n", IntStream.of(order)
                .mapToObj(Integer::valueOf)
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] GRAPH = new List[numCourses];
        //Arrays.fill(GRAPH, new ArrayList());
        for(int[] prerequisite:  prerequisites) {
            if(GRAPH[prerequisite[1]] == null) {
                GRAPH[prerequisite[1]] = new ArrayList();
            }
            GRAPH[prerequisite[1]].add(prerequisite[0]);
        }
        int[] visited = new int[numCourses];
        Arrays.fill(visited, -1);
        List<Integer> order = new ArrayList<>();
        for(int i = 0; i < numCourses; i++) {
            if(visited[i] == -1) {
                boolean isPossible = dfs(i, visited, order, GRAPH);
                if(!isPossible) {
                    return new int[0];
                }
            }
        }
        Collections.reverse(order);
        int i = 0;
        for(int course: order) {
            visited[i++] = course;
        }
        return visited;
    }

    private boolean dfs(int current, int[] visited, List<Integer> order, List<Integer>[] GRAPH) {
        if(visited[current] >= 0) {
            return true;
        }
        visited[current] = 0;
        List<Integer> neighbours = (GRAPH[current] != null) ? GRAPH[current] : Collections.EMPTY_LIST;
        for(int neighbour: neighbours) {
            //Cycle
            if(visited[neighbour]  == 0) {
                return false;
            }
            boolean isPossible = dfs(neighbour, visited, order, GRAPH);
            if(!isPossible) {
                return false;
            }
        }
        visited[current]= 1;
        order.add(current);
        return true;
    }

}
