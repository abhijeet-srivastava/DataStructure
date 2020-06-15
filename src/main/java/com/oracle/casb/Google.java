package com.oracle.casb;

import com.oracle.casb.common.ListNode;
import com.walmart.UnionFind;

import java.util.*;
import java.util.stream.Collectors;

public class Google {

    public static void main(String[] args) {
        Google google = new Google();
        //google.testDisLike();
        //google.testCountBit();
        //google.ufDemo();
        //google.testRevList();
        google.testJumpFrog();
    }

    private void testJumpFrog() {
        int[][] edges = {{2,1},{3,2}, {4,1}, {5,1}, {6,4}, {7,1}, {8,7}};
        //int[][] edges = {{1,2},{1,3}, {1,7}, {2,4}, {2,6}, {3,5}};
        double prob = frogPosition1(8, edges, 7, 7);
        //double prob = frogPosition1(7, edges, 2, 4);
        System.out.printf("%f\n", prob);
    }

    private void testRevList() {
        ListNode list = createList(new int[] {1,2,3,4,5,6,7});
        ListNode reverse = reverse(list, 2, 5);
        printList(reverse);
    }
    private void testRandomSet() {
        Map<Integer, Integer> map = new HashMap<>();
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        Map<Integer, Set<int[]>> GRAPH = new HashMap<>();
        for(int[] flight: flights) {
            GRAPH.computeIfAbsent(flight[0], HashSet::new).add(new int[]{flight[1], flight[2], Integer.MAX_VALUE, 0});
        }
        return -1;
    }

    public double frogPosition(int n, int[][] edges, int t, int target) {
        Map<Integer, Set<Integer>> GRAPH = new HashMap<>();
        for(int[] edge : edges) {
            GRAPH.computeIfAbsent(Math.min(edge[0], edge[1]), HashSet::new).add(Math.max(edge[0], edge[1]));
            //GRAPH.computeIfAbsent(edge[1], HashSet::new).add(edge[0]);
        }
        boolean[] visited = new boolean[n+1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        double[] prob = new double[n+1];
        prob[1] = 1f;
        while(!queue.isEmpty() && t > 0) {
            t -= 1;
            for(int i = 0; i < queue.size(); i++) {
                int u = queue.poll(), nextVerticesCount = 0;
                for (int v : GRAPH.get(u)) {
                    if (!visited[v]) {
                        nextVerticesCount++;
                        visited[v] = true;
                        queue.offer(v);
                        prob[v] = prob[u] / nextVerticesCount;
                    }
                }
            }
        }
        return prob[target];
    }

    public double frogPosition1(int n, int[][] edges, int t, int target) {
        Map<Integer, Set<Integer>> GRAPH = new HashMap<>();
        for(int[] edge : edges) {
            GRAPH.computeIfAbsent(Math.min(edge[0], edge[1]), HashSet::new).add(Math.max(edge[0], edge[1]));
            //GRAPH.computeIfAbsent(edge[0], HashSet::new).add(edge[1]);
            //GRAPH.computeIfAbsent(edge[1], HashSet::new).add(edge[0]);
        }
        boolean found = false;
        List<Integer> path = new ArrayList<>();
        found = findPath(1, target, GRAPH, path);
        if(!found ||  path.size() > t+1) {
            System.out.printf("found %s, %d\n", found ? "true" : "false", path.size());
            return 0.0d;
        }
        System.out.printf("%s\n ", path.stream().map(String::valueOf).collect(Collectors.joining(", ")));
        int val = 1;
        for(int i : path) {
            val *= (GRAPH.containsKey(i) ? GRAPH.get(i).size() : 1);
        }
        return Double.valueOf(1.0d/val);
    }

    private boolean findPath(int i, int target, Map<Integer, Set<Integer>> graph,  List<Integer> path) {
        boolean found = false;
        if(i == target) {
            path.add(i);
            return true;
        } else {
            if(!graph.containsKey(i)) {
                return found;
            }
            for(int neighbour : graph.get(i)) {
                found = findPath(neighbour, target, graph, path);
                if(found) {
                    path.add(0, i);
                    break;
                }
            }
        }
        return found;
    }

    private void printList(ListNode head) {
        while(head != null) {
            System.out.printf("%d, ", head.val, ',');
            head = head.next;
        }
        System.out.println();
    }

    private ListNode createList(int[] nums) {
        ListNode head = null;
        ListNode previous = head;
        for(int i : nums) {
            ListNode current = new ListNode(i);
            if(head == null) {
                head = current;
                previous = head;
            } else {
                previous.next = current;
                previous = current;
            }
        }
        return head;
    }


    public int[][] reconstructQueue(int[][] people) {
        List<int[]> sorted = Arrays.asList(people);
        List<int[]> result = new ArrayList<>(people.length);
        Arrays.sort(people, (p1,p2) -> (p1[0] == p2[0]) ? (p1[1]- p2[1]) : (p2[0]- p1[0]));
        for(int[] person : people) {
            result.add(person[1], person);
        }

        return result.toArray(new int[people.length][2]);
    }


    public  ListNode reverse(ListNode head, int m, int n){
        ListNode current = head;
        ListNode prev = null;
        for(int i = 1; i < m; i++) {
            prev = current;
            current = current.next;
        }
        prev.next = reverse(current, n-m);
        return head;
    }
    public  ListNode reverse(ListNode head, int n){
        ListNode current = head;
        ListNode next = current.next;
        for(int i = 1; i < n; i++) {
            ListNode tmp = next.next;
            next.next = current;
            current = next;
            next = tmp;
        }
        head.next = next;
        return current;
    }

    public int[] countBits(int num) {
        int[] res = new int[num+1];
        res[0] = 0;
        res[1] = 1;
        for(int i = 2; i <= num; i++) {
            if((i&1) == 0) {
                //Even Number
                res[i] = res[i >> 1];
            } else {
                //Odd Number
                res[i] = res[i >> 1] + 1;
            }
        }
        return res;
    }


    private void colour(int[][] M, int i, int j, int color) {
        for(int col = j-1; col >= 0; col--) {
            if(M[i][col] == 1) {
                M[i][col] = color;
                colour(M, i, col, color);
            } else {
                break;
            }
        }
        for(int col = j+1; col < M[i].length; col++) {
            if(M[i][col] == 1) {
                M[i][col] = color;
                colour(M, i, col, color);
            } else {
                break;
            }
        }

        for(int row = i-1; row >= 0; row--) {
            if(M[row][j] == 1) {
                M[row][j] = color;
                colour(M, row, j, color);
            } else {
                break;
            }
        }
        for(int row = i+1; row < M.length; row++) {
            if(M[row][j] == 1) {
                M[row][j] = color;
                colour(M, row, j, color);
            } else {
                break;
            }
        }
    }

    public boolean isBipartite1(int[][] graph) {
        int len = graph.length;
        int[] colors = new int[len];

        for (int i = 0; i < len; i++) {
            if (colors[i] != 0) continue;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(i);
            colors[i] = 1;   // Blue: 1; Red: -1.

            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (int next : graph[cur]) {
                    if (colors[next] == 0) {          // If this node hasn't been colored;
                        colors[next] = -colors[cur];  // Color it with a different color;
                        queue.offer(next);
                    } else if (colors[next] != -colors[cur]) {   // If it is colored and its color is different, return false;
                        return false;
                    }
                }
            }
        }

        return true;
    }
    public boolean isBipartite(int[][] graph) {
        boolean isBipartite = true;
        int length = graph.length;
        int[] color = new int[length];
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < length; i++) {
            if(color[i] != 0) {//Already Visited
                continue;
            }
            color[i] = 1;
            queue.offer(i);
            while (!queue.isEmpty()) {
                int current = queue.poll();
                for (int neighbour : graph[current]) {

                    if(color[neighbour] == 0) {
                        color[neighbour] = -color[current];
                        queue.offer(neighbour);
                    }else if (color[neighbour] != -color[current]) {
                        isBipartite = false;
                        break;
                    }
                }
                if(!isBipartite) {
                    break;
                }
            }
            if(!isBipartite) {
                break;
            }
        }

        return isBipartite;
    }

    public boolean possibleBipartition(int N, int[][] dislikes) {
        boolean isPossible = true;
        Map<Integer, Set<Integer>> GRAPH = new HashMap<>();
        for(int[] dislike : dislikes) {
            GRAPH.computeIfAbsent(dislike[0], HashSet::new).add(dislike[1]);
            GRAPH.computeIfAbsent(dislike[1], HashSet::new).add(dislike[0]);
        }
        int[] color = new int[N+1];
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 1; i <= N; i++) {
            if(color[i] != 0) {
                continue;
            }
            queue.offer(i);
            color[i] = 1;
            while (!queue.isEmpty()) {
                int current = queue.poll();
                if(!GRAPH.containsKey(current)) {
                    continue;
                }
                for(int neighbour : GRAPH.get(current)) {
                    if(color[neighbour] == color[current]) {
                        isPossible = false;
                        break;
                    }
                    if(color[neighbour] == 0) {
                        color[neighbour] = -color[current];
                        queue.offer(neighbour);
                    }
                }
                if(!isPossible) {
                    break;
                }
            }
            if(!isPossible) {
                break;
            }
        }
        return isPossible;
    }

    private void testCountBit() {
        int[] result = countBits(5);
        for(int i : result) {
            System.out.printf("%d, ", i);
        }
        System.out.printf("%c", '\n');
    }
    private void testDisLike() {
        int[][] dislikes = {{1,2}, {1,3}, {2,3}};
        System.out.println(possibleBipartition(3, dislikes));
    }

    private void ufDemo() {
        int[][] friendMatrix = {{1,1,0}, {1,1,1}, {0,1,1}};
        int count = findCircleNum(friendMatrix);
        System.out.printf("%d", count);
    }

    public void dfs(int[][] M, int[] visited, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;
                dfs(M, visited, j);
            }
        }
    }
    public int findCircleNum(int[][] M) {
        int[] visited = new int[M.length];
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (visited[i] == 0) {
                dfs(M, visited, i);
                count++;
            }
        }
        return count;
    }

    private int findCircleNum1(int[][] M) {
        int n = M.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (M[i][j] == 1) uf.union(i, j);
            }
        }
        return uf.count();
    }

    public int shortestBridge(int[][] A) {
        int R = A.length, C = A[0].length;
        int[][] colors = getComponents(A);

        Queue<Node> queue = new LinkedList();
        Set<Integer> target = new HashSet();

        for (int r = 0; r < R; ++r)
            for (int c = 0; c < C; ++c) {
                if (colors[r][c] == 1) {
                    queue.add(new Node(r, c, 0));
                } else if (colors[r][c] == 2) {
                    target.add(r * C + c);
                }
            }

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (target.contains(node.r * C + node.c))
                return node.depth - 1;
            for (int nei: neighbors(A, node.r, node.c)) {
                int nr = nei / C, nc = nei % C;
                if (colors[nr][nc] != 1) {
                    queue.add(new Node(nr, nc, node.depth + 1));
                    colors[nr][nc] = 1;
                }
            }
        }

        throw null;
    }

    public int[][] getComponents(int[][] A) {
        int R = A.length, C = A[0].length;
        int[][] colors = new int[R][C];
        int t = 0;

        for (int r0 = 0; r0 < R; ++r0)
            for (int c0 = 0; c0 < C; ++c0)
                if (colors[r0][c0] == 0 && A[r0][c0] == 1) {
                    // Start dfs
                    Stack<Integer> stack = new Stack();
                    stack.push(r0 * C + c0);
                    colors[r0][c0] = ++t;

                    while (!stack.isEmpty()) {
                        int node = stack.pop();
                        int r = node / C, c = node % C;
                        for (int nei: neighbors(A, r, c)) {
                            int nr = nei / C, nc = nei % C;
                            if (A[nr][nc] == 1 && colors[nr][nc] == 0) {
                                colors[nr][nc] = t;
                                stack.push(nr * C + nc);
                            }
                        }
                    }
                }

        return colors;
    }

    public List<Integer> neighbors(int[][] A, int r, int c) {
        int R = A.length, C = A[0].length;
        List<Integer> ans = new ArrayList();
        if (0 <= r-1) ans.add((r-1) * R + c);
        if (0 <= c-1) ans.add(r * R + (c-1));
        if (r+1 < R) ans.add((r+1) * R + c);
        if (c+1 < C) ans.add(r * R + (c+1));
        return ans;
    }
}

class Node {
    int r, c, depth;
    Node(int r, int c, int d) {
        this.r = r;
        this.c = c;
        depth = d;
    }


}



