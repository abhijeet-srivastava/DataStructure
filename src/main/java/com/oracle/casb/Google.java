package com.oracle.casb;

import com.oracle.casb.common.ListNode;
import com.walmart.UnionFind;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Google {

    public static void main(String[] args) {
        Google google = new Google();
        //google.testNextNum();
        //google.testPrisonAfterNDays();
        //google.testDisLike();
        //google.testCountBit();
        //google.ufDemo();
        //google.testRevList();
        //google.testJumpFrog();
        google.testSweetNess();
    }

    private void testSweetNess() {
        /*int[] arr = {1,1,1,1,2,8,2,3};
        int k = 3;*/
        /*int[] arr = {3, 4, 5, 3, 7, 2};
        int k = 3;*/
        int[] arr = {1,1,8,1,2,2,2,3,1};
        int k = 4;
        int max = maxSweetness(arr, k);
        System.out.printf("Max Sweetness :%d\n", max);
    }

    private int maxSweetness(int[] chocolate, int k) {
        int sum = IntStream.of(chocolate).sum();
        int avg = sum/k;
        int hi = avg;
        int lo = 1;
        int mid = lo + (hi-lo)/2;//Lowest possible Sweetness
        while(lo < hi) {
            int portions = 1;
            //Task is to find k-1 portion having remaining average
            int currentSweetness = 0;
            java.util.List<Integer> sweetNess = new ArrayList<>();
            for(int portionIndex = 0; portionIndex < chocolate.length; portionIndex++) {
                currentSweetness += chocolate[portionIndex];
                if(currentSweetness  > mid) {
                    sweetNess.add(currentSweetness);
                    currentSweetness = 0;
                    portions += 1;
                }
            }
            if(portions == k) {
                break;
            } else if(portions > k) {//More portions; Increment Own piece sweetness
                lo = mid+1;
            } else {//Less Portions, Decrement;Yours sweetness
                hi = mid;
            }
            mid = lo + (hi-lo)/2;//Lowest possible Sweetness
        }
        return mid;
    }
    /**
     * https://community.topcoder.com/stat?c=problem_statement&pm=1901&rd=4650
     * Method:	getMostWork
     * Parameters:	int[], int
     * Returns:	int
     * Method signature:	int getMostWork(int[] folders, int workers)
     * (be sure your method is public)
     * @param folders
     * @param workers
     * @return
     */
    public int getMostWork(int[] folders, int workers) {
        //Minimum work is: Max of folders, Each folder has to be done, maximum folder done alone
        int min = IntStream.of(folders).max().getAsInt();
        //Maximum work: Sum of All the folders
        int max = IntStream.of(folders).sum();
        //Optimum workload lies in between, Try binary search
        while (min < max) {
            int workLoad = min + (max-min)/2;
            //Calculate number of workers with given workload
            int requiredWorkers = 1;
            int currentWl = 0;
            for(int folder : folders) {
                if((currentWl + folder) >  workLoad) {
                    //Reset current Workload
                    currentWl = 0;
                    //Increment number of Workers
                    requiredWorkers += 1;
                }
                //Add them in currentWl
                currentWl += folder;
            }
            //Check with given workload; number of workers
            if(requiredWorkers > workers) {
                //Since with given workload, more number of workers are required
                //Current workload Should be incremented
                min = workLoad+1;
            } else if(requiredWorkers <= workers) {
                //Since with given workload, lesser workers are employable
                //Decrement Workload
                max = workLoad;
            }
        }
        return min;
    }
    private void testJumpFrog() {
        int[][] edges = {{2,1},{3,2}, {4,1}, {5,1}, {6,4}, {7,1}, {8,7}};
        //int[][] edges = {{1,2},{1,3}, {1,7}, {2,4}, {2,6}, {3,5}};
        double prob = frogPosition1(8, edges, 7, 7);
        //double prob = frogPosition1(7, edges, 2, 4);
        System.out.printf("%f\n", prob);
    }

    private void testRevList() {
        ListNode list = createList(new int[] {3,5});
        ListNode reverse = reverse(list, 1, 2);
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
        java.util.List<Integer> path = new ArrayList<>();
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

    private boolean findPath(int i, int target, Map<Integer, Set<Integer>> graph,  java.util.List<Integer> path) {
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
        java.util.List<int[]> sorted = Arrays.asList(people);
        java.util.List<int[]> result = new ArrayList<>(people.length);
        Arrays.sort(people, (p1,p2) -> (p1[0] == p2[0]) ? (p1[1]- p2[1]) : (p2[0]- p1[0]));
        for(int[] person : people) {
            result.add(person[1], person);
        }

        return result.toArray(new int[people.length][2]);
    }


    public  ListNode reverse(ListNode head, int m, int n){
        if((n-m) < 2) {
            return head;
        }
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
        for(int i = 1; i <= n; i++) {
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

    public java.util.List<Integer> neighbors(int[][] A, int r, int c) {
        int R = A.length, C = A[0].length;
        java.util.List<Integer> ans = new ArrayList();
        if (0 <= r-1) ans.add((r-1) * R + c);
        if (0 <= c-1) ans.add(r * R + (c-1));
        if (r+1 < R) ans.add((r+1) * R + c);
        if (c+1 < C) ans.add(r * R + (c+1));
        return ans;
    }


    public int nthUglyNumber(int n) {
        int number = 1;
        int[] values = {2,3,5};
        Queue<Integer> queue = new PriorityQueue<>((a,b) -> a.compareTo(b));
        queue.add(1);
        List list = new List();
        while(list.getSize() < n && !queue.isEmpty()) {
            int current = queue.poll();
            list.insert(current);
            for(int val : values) {
                queue.offer(val * current);
            }
        }
        return list.getLast().getValue();
    }
    private class List {
        private int size;
        private Node head;
        private Node last;

        public List() {
            this.size = 0;
            this.head = null;
            this.last = null;
        }
        public void insert(int number) {
            if(this.size == 0) {
                Node node = new Node(number);
                this.head = node;
                this.last = node;
                this.size += 1;
            } else {
                Node current = head;
                Node previous = null;
                while(current != null
                        && current.value < number) {
                    previous = current;
                    current = current.next;
                }
                if(current == null) {
                    Node node = new Node(number);
                    last.next = node;
                    last = last.next;
                    size += 1;
                } else if(current.value > number) {
                    Node node = new Node(number);
                    if(previous == null) {
                        node.setNext(previous.getNext());
                        previous.setNext(node);
                    } else {
                        node.setNext(this.head);
                        head = node;
                    }
                    size += 1;
                } else {
                    //System.out.printf("Duplicate value :%d\n", number);
                    return;
                }
            }
        }
        public Node getNthNode(int n) {
            Node current = this.head;
            while(n > 0 && current != null){
                current = current.next;
                n -= 1;
            }
            return current;
        }
        public int getSize() {
            return size;
        }

        public Node getHead() {
            return head;
        }

        public Node getLast() {
            return last;
        }

        private  class Node {
            private int value;
            private Node next;

            public Node(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }
        }
    }

    private int findLeast(int[] current, int number) {
        int index = 0;
        for(int i = 0; i < current.length; i++) {
            if(current[i] < current[index] && current[i] > number) {
                index = i;
            }
        }
        return index;
    }

    public void testPrisonAfterNDays() {
        int i = 255;
        System.out.printf("%d ugly number %d\n", i, nthUglyNumber(i));
        /*for(int i = 1; i <= 15; i++) {
            System.out.printf("%d ugly number %d\n", i, nthUglyNumber(i));
        }*/
        //int nUgly = nthUglyNumber(2);
        // System.out.println(nUgly);
        /*int[] arr = {0,1,0,1,1,0,0,1};
        int[] res= prisonAfterNDays(arr, 7);*/
    }
    public int[] prisonAfterNDays(int[] cells, int N) {
        int[] res = new int[cells.length];
        for(int i = 0; i <= N; i++) {
            System.out.printf("Day %d: %s\n", i, Arrays.stream(cells).boxed().map(String::valueOf).collect(Collectors.joining(", ", "[", "]")));
            res[0] = 0;
            res[7] = 0;
            res[1] = (cells[0]^cells[2]) == 0 ? 1 : 0;
            res[2] = (cells[1]^cells[3]) == 0 ? 1 : 0;
            res[3] = (cells[2]^cells[4]) == 0 ? 1 : 0;
            res[4] = (cells[3]^cells[5]) == 0 ? 1 : 0;
            res[5] = (cells[4]^cells[6]) == 0 ? 1 : 0;
            res[6] = (cells[5]^cells[7]) == 0 ? 1 : 0;

            cells = res;
            res = new int[8];
        }
        return cells;
    }

    public int nextHighest(int n) {
        char[] array = String.valueOf(n).toCharArray();
        int i = array.length - 1;
        while (i > 0) {
            if (Character.getNumericValue(array[i]) > Character.getNumericValue(array[i - 1])) {
                break;
            }
        }
        if (i == 0) {
            return -1;
        }
        //swap(array, i, i-1);
        return -1;
    }

    private  int minSwaps(int[] a, int[] b, int size) {
        Arrays.sort(a);
        Arrays.sort(b);
        int minCost = 0;
        int x = 0;
        for(;x < size; x++) {
            if(a[x] == b[x]) {
                continue;
            } else if(a[x] < b[x]) {
                if(x < size-1 && a[x+1] == a[x]) {
                    int tmp = b[x];
                    b[x] = a[x+1];
                    a[x+1] = tmp;
                }
            } else {

            }
        }
        return -1;
    }

    //   List<String> getNextLevelStrings(String s) {
//     List<String> l = new ArrayList<>();
//     int n = s.length();
//     for (int i = 1; i < n; i++) {
//       if (s.charAt(i) < s.charAt(i-1)) {
//           StringBuffer sb = new StringBuffer(s);
//           char tmp = sb.charAt(i);
//           sb.setCharAt(i, sb.charAt(i-1));
//           sb.setCharAt(i-1, tmp);
//           l.add(sb.toString());
//       }
//     }
//     return l;
//   }
//   //k * (n-1)

//   int minimumNumberInKSteps(int n, int k) {
//       String s = Integer.valueOf(n).toString();
//       int min = n;
//       int steps = k;
//       Queue<String> q = new LinkedList<>();
//       q.offer(s);
//       while ((!q.isEmpty()) && (steps > 0)) {
//           steps--;
//           int size = q.size();
//           while (size > 0) {
//             String next = q.poll();
//             int val = Integer.valueOf(next);
//             min = Math.min(val, min);
//             List<String> nextLevel = getNextLevelStrings(next);
//             for (String nextLevelString : nextLevel) {
//                 q.offer(nextLevelString);
//             }
//             size--;
//           }
//       }
//       return min;
//   }

    private void testNextNum() {
        String nextMin = minInteger("4321", 4);
        System.out.printf("%s\n", nextMin);
    }
    public String minInteger(String num, int k) {
        Queue<String> queue = new LinkedList<String>();
        queue.offer(num);
        Set<String> set = new HashSet<>();
        set.add(num);
        BigInteger min = new BigInteger(num);
        String minStr = num;
        int steps = k+1;
        while(!queue.isEmpty() && steps-- > 0) {
            int size = queue.size();
            while(size-- > 0) {
                String curr = queue.poll();
                BigInteger current = new BigInteger(curr);
                if(current.compareTo(min) < 0) {
                   min = current;
                   minStr = curr;
                }
                Set<String> nextLevel = getNextLevel(curr, set);
                queue.addAll(nextLevel);
            }
        }
        return String.valueOf(min);
    }

    private Set<String> getNextLevel(String num, Set<String> numSet) {
        char[] array = num.toCharArray();
        Set<String> set = new HashSet<>();
        for(int i = 1; i < num.length(); i++) {
            if(array[i] < array[i-1]) {
                swap(array, i, i-1);
                String val = String.valueOf(array);
                if(!numSet.contains(val)) {
                    numSet.add(val);
                    set.add(val);
                }
                swap(array, i, i-1);
            }
        }
        return set;
    }
    private void swap(char[] array, int i, int j) {
        char tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    static int WHITE = 1;
    static int GRAY = 2;
    static int BLACK = 3;

    boolean isPossible;
    Map<Integer, Integer> color;
    Map<Integer, java.util.List<Integer>> adjList;
    java.util.List<Integer> topologicalOrder;

    private void init(int numCourses) {
        this.isPossible = true;
        this.color = new HashMap<Integer, Integer>();
        this.adjList = new HashMap<Integer, java.util.List<Integer>>();
        this.topologicalOrder = new ArrayList<Integer>();

        // By default all vertces are WHITE
        for (int i = 0; i < numCourses; i++) {
            this.color.put(i, WHITE);
        }
    }

    private void dfs(int node) {

        // Don't recurse further if we found a cycle already
        if (!this.isPossible) {
            return;
        }

        // Start the recursion
        this.color.put(node, GRAY);

        // Traverse on neighboring vertices
        for (Integer neighbor : this.adjList.getOrDefault(node, new ArrayList<Integer>())) {
            if (this.color.get(neighbor) == WHITE) {
                this.dfs(neighbor);
            } else if (this.color.get(neighbor) == GRAY) {
                // An edge to a GRAY vertex represents a cycle
                this.isPossible = false;
            }
        }

        // Recursion ends. We mark it as black
        this.color.put(node, BLACK);
        this.topologicalOrder.add(node);
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {

        this.init(numCourses);

        // Create the adjacency list representation of the graph
        for (int i = 0; i < prerequisites.length; i++) {
            int dest = prerequisites[i][0];
            int src = prerequisites[i][1];
            java.util.List<Integer> lst = adjList.getOrDefault(src, new ArrayList<Integer>());
            lst.add(dest);
            adjList.put(src, lst);
        }

        // If the node is unprocessed, then call dfs on it.
        for (int i = 0; i < numCourses; i++) {
            if (this.color.get(i) == WHITE) {
                this.dfs(i);
            }
        }

        int[] order;
        if (this.isPossible) {
            order = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                order[i] = this.topologicalOrder.get(numCourses - i - 1);
            }
        } else {
            order = new int[0];
        }

        return order;
    }

    private void reverse() {

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



