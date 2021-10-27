package com.walmart.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<List<Integer>> adjencyList;

    int id = 0;

    private void findBridges() {
        List<Integer> bridges = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        List<Integer> low = new ArrayList<>();
        List<Boolean> visited = new ArrayList<>();
        for(int i = 0; i < adjencyList.size(); i++){
            if(!visited.get(i-1)) {
                dfs(i, -1, ids, low, visited,bridges);
            }
        }
    }

    private void dfs(int current, int parent, List<Integer> ids, List<Integer> low, List<Boolean> visited, List<Integer> bridges) {
        visited.set(current-1, Boolean.TRUE);
        id = id+1;
        ids.set(current-1, id);
        low.set(current-1, id);


        for(int neighbour : adjencyList.get(current-1)) {
            if(neighbour == parent) {
                continue;
            } else if(!visited.get(current-1)) {
                dfs(neighbour, current, ids, low, visited, bridges);
                low.set(current-1, Math.min(low.get(current-1), low.get(neighbour-1)));
                if(ids.get(current-1) < ids.get(neighbour-1)) {
                    bridges.add(current);
                    bridges.add(neighbour);
                }
            } else {
                low.set(current-1, Math.min(low.get(current), ids.get(neighbour-1)));
            }
        }
    }


}
