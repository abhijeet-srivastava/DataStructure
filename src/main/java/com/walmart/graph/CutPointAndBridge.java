package com.walmart.graph;

import java.io.InputStream;
import java.util.*;

public class CutPointAndBridge {
     GRAPH g;

     int[] preOrderNum;
     int[] lowLinkVal;
     boolean[] cutPoints;
     Set<Edge> bridges;
     int counter;


    public static void main(String[] args) {
        CutPointAndBridge cab = new CutPointAndBridge();
        cab.Test(new Scanner(System.in));
    }

    private void Test(Scanner in) {
        int V = in.nextInt();
        this.g = new GRAPH(V);
        int numEdges = in.nextInt();
        while(numEdges-- > 0) {
            int i = in.nextInt();
            int j = in.nextInt();
            g.addEdge(i, j);
        }
        preOrderNum = new int[V];
        Arrays.fill(preOrderNum, -1);
        lowLinkVal = new int[V];
        Arrays.fill(lowLinkVal, -1);
        cutPoints = new boolean[V];
        bridges = new HashSet<>();

        for(int i = 0; i < V; i++) {
            if(preOrderNum[i] != -1) {
                dfs(i, i, g.E);
            }
        }
    }

    private int dfs(int current, int parent, int edgeId) {
        if(preOrderNum[current] != -1) {
            //Already Visited; current --> parent is back edge
            lowLinkVal[parent] = Math.min(lowLinkVal[parent], preOrderNum[current]);
            return lowLinkVal[parent];
        }
        preOrderNum[current] = counter;
        lowLinkVal[current] = counter;
        counter += 1;
        boolean hasFwdEdge = false;
        for(Edge edge : g.adj[current]) {
            if(edge.id == edgeId) {
                //Self loop
                continue;
            }
            if(dfs(edge.u, current, edge.id) < 0) {
                lowLinkVal[current] = Math.min(lowLinkVal[current], lowLinkVal[edge.u]);
                if(lowLinkVal[edge.u] == preOrderNum[edge.u]) {
                    // Is a Bridge
                    bridges.add(edge);
                }
                /**
                 * 1. If root node then - at leas two forward edges
                 * 2. Or lowlinkValue of child >= preOrderValue of current
                 */
                boolean isCutPoint = (current == parent) ? hasFwdEdge : lowLinkVal[edge.u] >= preOrderNum[current];
                if(isCutPoint) {
                    cutPoints[current] = true;
                }
                hasFwdEdge = true;
            }
        }
        return -1;
    }

    class GRAPH {
        int V;
        int E;
        ArrayList<Edge>[] adj;

        public GRAPH(int v) {
            this.V = v;
            this.E = 0;
            adj = new ArrayList[V];
            for(int i = 0; i < V; i++) {
                adj[i] = new ArrayList<>();
            }
        }
        void addEdge(int i, int j) {
            adj[i].add(new Edge(i, j, E));
            adj[j].add(new Edge(i, j, E));
            E += 1;
        }
    }

    class Edge {
        int id;
        int u;
        int v;

        public Edge(int u, int v, int id) {
            this.id = id;
            this.u = u;
            this.v = v;
        }
    }
}
