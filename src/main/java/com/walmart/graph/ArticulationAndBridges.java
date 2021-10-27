package com.walmart.graph;

import java.util.ArrayList;
import java.util.List;

//https://codeforces.com/blog/entry/68138
public class ArticulationAndBridges {

    private  final List<Integer>[] GRAPH;
    int[] DP;
    int[] LEVEL;

    int bridgeCount = 0;
    boolean[] articulationPoint;

    public ArticulationAndBridges(int v, int e, List<int[]> edges) {
        this.GRAPH = new List[v];
        for(int[] edge : edges) {
            if(GRAPH[edge[0] -1] == null) {
                GRAPH[edge[0] -1] = new ArrayList<>();
            }
            GRAPH[edge[0] -1].add(edge[1]);
            if(GRAPH[edge[1] -1] == null) {
                GRAPH[edge[1] -1] = new ArrayList<>();
            }
            GRAPH[edge[1] -1].add(edge[0]);
        }
        this.DP = new int[v];
        this.LEVEL = new int[v];
        this.articulationPoint = new boolean[v];
    }

    private void dfs(int  parent) {
        DP[parent-1] = 0;
        for(int  child : GRAPH[parent-1]) {
            if(LEVEL[child-1] == 0) {
                /* Parent Child Edge */
                LEVEL[child-1] = LEVEL[parent-1] + 1;
                dfs(child);

                DP[parent-1] += DP[child-1];
            } else if(LEVEL[child-1] < LEVEL[parent-1]) {
                /* Back Edge Going down */
                DP[parent-1] += 1;
            } else if (LEVEL[child-1] > LEVEL[parent-1]) {
                /* Back Edge going UP*/
                DP[parent-1] -= 1;
            }
        }
        // Exclude the parent - child edge
        DP[parent-1]--;
        if(DP[parent-1] == 0 && LEVEL[parent-1] > 1) {
            bridgeCount += 1;
        }
    }
    private void dfsCount(int parent, boolean[] visited, int maxLevel) {
        visited[parent-1] = true;
        for(int child : GRAPH[parent-1]) {
            /*If dp[child] = 0 then it contains a bridge, add both the parent and child*/
            /*For excluding the leaf vertices*/
            if(!visited[child-1]
                    && LEVEL[parent - 1] > 1
                    && DP[child-1]==0 && LEVEL[child-1] != maxLevel){
                articulationPoint[child-1] = true;
                articulationPoint[parent-1] = true;
            }
            if(!visited[child-1]){
                dfsCount(child, visited, maxLevel);
            }
        }
    }
}
