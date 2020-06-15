package com.oracle.casb.leetcode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Contest123 {

    public static void main(String[] args) {
        Contest123 ct = new Contest123();

        //ct.testTraversal();
           /* int N = 16;
            int x = (N==0) ? 1 : (~(N<<(N=Integer.numberOfLeadingZeros(N))))>>N;
            int num = 16;
            System.out.println("Leading: " + Integer.numberOfLeadingZeros(num) + ", Trailing: " + Integer.numberOfTrailingZeros(num) );*/
        //ct.testCousin();
        //ct.testMinSize();
        //ct.testCreateBstFromPreOrder();
        //ct.testSubSet();
        //ct.testReverseStr();
        //ct.testLines();
        //ct.testJudge();
        //ct.testCourseDependency();
        //ct.testInvertTree();
        //ct.testMaxCutArea();
        //ct.testMinOrder();
        ct.testCoinChange();
    }

    private void testCoinChange() {
        int[] coins = {1, 2, 5};
        int count = change(5, coins);
    }


    private void testMaxCutArea() {
        int[] horizontalCuts = {1, 2, 4}, verticalCuts = {1, 3};
        int area = maxArea(5, 4, horizontalCuts, verticalCuts);
        System.out.println(area);
    }

    private void testMinOrder() {
        int[][] connections = {{0, 1}, {2, 1}, {3, 2}, {0, 4}, {5, 1}, {2, 6}, {5, 7}, {3, 8}, {8, 9}};
        //int[][] connections =   { {0,1 }, {1,3 }, {2,3 }, {4,0 }, {4,5 } };
        int count = minReorder(10, connections);
        System.out.println(count);
    }

    public int minReorder(int n, int[][] connections) {
        int count = 0;
        Map<Integer, Set<int[]>> map = new HashMap<>();
        for (int[] connection : connections) {
            map.computeIfAbsent(connection[0], HashSet::new).add(new int[]{connection[1], 1});
            map.computeIfAbsent(connection[1], HashSet::new).add(new int[]{connection[0], 0});
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            queue.offer(i);
            while (!queue.isEmpty()) {
                int current = queue.poll();
                if (visited[current] || !map.containsKey(current)) {
                    continue;
                }
                visited[current] = true;
                for (int[] connection : map.get(current)) {
                    if (visited[connection[0]]) {
                        continue;
                    }
                    count += connection[1];
                    queue.offer(connection[0]);
                }
            }
        }
        return count;
    }

    public int change(int amount, int[] coins) {
        int[][] DP = new int[coins.length + 1][amount + 1];
        //Create any Amount with 0 coins : 0
        //Arrays.fill(DP[0], 0);
        //Number of ways to create amount 0= 1(Don't choose any coin)
            /*for(int i = 0; i <= coins.length; i++) {
                DP[i][0] = 1;
            }*/
        DP[0][0] = 1;
        for (int numCoins = 1; numCoins <= coins.length; numCoins++) {
            DP[numCoins][0] = 1;
            for (int amnt = 1; amnt <= amount; amnt++) {
                DP[numCoins][amnt] = DP[numCoins - 1][amnt];
                if (amnt >= coins[numCoins - 1]) {
                    DP[numCoins][amnt] += DP[numCoins][amnt - coins[numCoins - 1]];
                }
            }
        }

        return DP[coins.length][amount];
    }

    public int[] getStrongest(int[] arr, int k) {
        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);
        int median = arr[(arr.length - 1) / 2];
        Integer[] stream = Arrays.stream(arr).boxed().sorted((a,b) ->
                (Math.abs(a-median) == Math.abs(b-median))
                        ? ((a > b) ? a : b)
                        :  (Math.abs(a-median) > Math.abs(b-median)) ? a : b).limit(k)
                .toArray(Integer[]::new);
        int[] res = new int[k];
        int index = 0;
        for(int i : stream) {
            res[index++] = i;
        }
        return res;
    }

    public List<Integer> largestDivisibleSubset(int[] nums) {
        int[] dp = new int[nums.length]; // the length of largestDivisibleSubset that ends with element i
        int[] prev = new int[nums.length]; // the previous index of element i in the largestDivisibleSubset ends with element i

        Arrays.sort(nums);

        int max = 0;
        int index = -1;
        for (int i = 0; i < nums.length; i++){
            dp[i] = 1;
            prev[i] = -1;
            for (int j = i - 1; j >= 0; j--){
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]){
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > max){
                max = dp[i];
                index = i;
            }
        }
        List<Integer> res = new ArrayList<Integer>();
        while (index != -1){
            res.add(nums[index]);
            index = prev[index];
        }
        return res;
    }

    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        int lenh = horizontalCuts.length;
        int leny = verticalCuts.length;
        List<Integer> sortHorz = Arrays.stream(horizontalCuts).boxed().sorted().collect(Collectors.toList());
        List<Integer> sortVert = Arrays.stream(verticalCuts).boxed().sorted().collect(Collectors.toList());

        int maxh = Math.max(sortHorz.get(0), h - sortHorz.get(lenh - 1));
        int maxy = Math.max(sortVert.get(0), w - sortVert.get(leny - 1));

        for (int i = 1; i < lenh; i++) {
            int diff = sortHorz.get(i) - sortHorz.get(i - 1);
            maxh = Math.max(maxh, diff);
        }

        for (int i = 1; i < leny; i++) {
            int diff = sortVert.get(i) - sortVert.get(i - 1);
            maxy = Math.max(maxy, diff);
        }
        return maxh * maxy;
    }

    private void testInvertTree() {
        TreeNode root = createTreeInvert();
        TreeNode inverted = invertTree(root);
        System.out.printf("%s\n", printTree(root));
    }

    private String printTree(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        sb.append(root);
        return sb.toString();
    }

    private TreeNode createTreeInvert() {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);
        return root;
    }


    public TreeNode invertTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        TreeNode invertLeft = invertTree(root.left);
        TreeNode invertRight = invertTree(root.right);
        root.right = invertLeft;
        root.left = invertRight;
        /*if(root.left != null) {
            root.right = invertTree(root.left);
        }
        if(root.right != null) {
            root.left = invertTree(root.right);
        }*/
        return root;
    }

    private void testCourseDependency() {
        int[][] courses = {{1, 0}, {0, 1}};
        System.out.println("Result " + canFinish(2, courses));
    }


    public int minimum(int a, int b, int c) {
        return Integer.min(a, Integer.min(b, c));
    }

    // Function to find Levenshtein Distance between String X and Y
    // m and n are the number of characters in X and Y respectively
    public int dist(String X, int m, String Y, int n) {
        // base case: empty strings (case 1)
        if (m == 0) {
            return n;
        }
        if (n == 0) {
            return m;
        }
        // if last characters of the strings match (case 2)
        int cost = (X.charAt(m - 1) == Y.charAt(n - 1)) ? 0 : 1;
        return minimum(dist(X, m - 1, Y, n) + 1,  // deletion (case 3a))
                dist(X, m, Y, n - 1) + 1,         // insertion (case 3b))
                dist(X, m - 1, Y, n - 1) + cost); // substitution (case 2 & 3c)
    }

    public int minDistance(String word1, String word2) {
        int[][] DP = new int[word1.length() + 1][word2.length() + 1];

        for (int row = 1; row <= word1.length(); row++) {
            DP[row][0] = row;
        }
        for (int col = 1; col <= word2.length(); col++) {
            DP[0][col] = col;
        }
        for (int row = 1; row <= word1.length(); row++) {
            for (int col = 1; col <= word2.length(); col++) {
                int cost = (word1.charAt(row - 1) == word2.charAt(col - 1)) ? 0 : 1;
                DP[row][col] = minimum(DP[row - 1][col] + 1,  // deletion
                        DP[row][col - 1] + 1,            // insertion
                        DP[row - 1][col - 1] + cost);
            }
        }
        return DP[word1.length()][word2.length()];
    }

    public int minDistance(char[] word1, int start1, char[] word2, int start2, Map<String, Integer> map) {
        if (start1 >= word1.length) {
            return word2.length - start2 + 1;
        } else if (start2 > word2.length) {
            return word1.length - start1 + 1;
        }
        String key = String.valueOf(start1).concat("_").concat(String.valueOf(start2));
        if (map.containsKey(key)) {
            return map.get(key);
        }
        int minDistance = Integer.MAX_VALUE;
        if (word1[start1] == word2[start2]) {
            minDistance = minDistance(word1, start1 + 1, word2, start2 + 1, map);
        } else {
            int edit = minDistance(word1, start1 + 1, word2, start2 + 1, map);
                /*Math.min(
                        minDistance(word1, start1+1, word2, start2, map),
                        minDistance(word1, start1, word2, start2+1, map));*/
            int delete = Math.min(
                    minDistance(word1, start1 + 1, word2, start2, map),
                    minDistance(word1, start1, word2, start2 + 1, map)
            );
            int insert = Math.min(
                    minDistance(word1, start1 + 1, word2, start2, map),
                    minDistance(word1, start1, word2, start2 + 1, map)
            );
            int min = Math.min(edit, Math.min(insert, delete)) + 1;
            minDistance = Math.min(minDistance, min);
        }
        map.put(key, minDistance);
        return minDistance;
    }

    public int slidingPuzzle(int[][] board) {
        String target = "123450";
        String start
                = Arrays.stream(board).flatMap(a -> Arrays.stream(a).boxed()).map(String::valueOf).collect(Collectors.joining());
        ;

            /*for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    start += board[i][j];
                }
            }*/
        HashSet<String> visited = new HashSet<>();
        // all the positions 0 can be swapped to
        int[][] dirs = new int[][]{
                {1, 3},
                {0, 2, 4},
                {1, 5},
                {0, 4},
                {1, 3, 5},
                {2, 4}
        };
        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        int res = 0;
        while (!queue.isEmpty()) {
            // level count, has to use size control here, otherwise not needed
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                if (cur.equals(target)) {
                    return res;
                }
                int zero = cur.indexOf('0');
                // swap if possible
                for (int dir : dirs[zero]) {
                    String next = swap(cur, zero, dir);
                    if (visited.contains(next)) {
                        continue;
                    }
                    visited.add(next);
                    queue.offer(next);
                }
            }
            res++;
        }
        return -1;
    }

    private String swap(String str, int i, int j) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i, str.charAt(j));
        sb.setCharAt(j, str.charAt(i));
        return sb.toString();
    }

    public int[][] kClosest(int[][] points, int K) {
        return Arrays.stream(points)
                .sorted((p1, p2) -> ((p1[0] * p1[0] + p1[1] * p1[1]) - (p2[0] * p2[0] + p2[1] * p2[1])))
                .limit(K).collect(Collectors.toList()).toArray(new int[K][2]);
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, Set<Integer>> dependencies = new HashMap<>();
        for (int[] prerequisite : prerequisites) {
            dependencies.computeIfAbsent(prerequisite[0], HashSet::new).add(prerequisite[1]);
        }
        boolean[] visited = new boolean[numCourses];
        boolean canFinish = true;
        boolean[] recStack = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (!canFinish(i, dependencies, visited, recStack)) {
                canFinish = false;
                break;
            }
        }

        return canFinish;
    }

    private boolean canFinish(int course, Map<Integer, Set<Integer>> dependencies, boolean[] visited,
                              boolean[] recStack) {
        if (recStack[course]) {
            return false;
        }
        if (visited[course] || !dependencies.containsKey(course)) {
            return true;
        }
        visited[course] = true;
        for (Integer dependency : dependencies.get(course)) {
            if (!canFinish(dependency, dependencies, visited, recStack)) {
                return false;
            }
        }
        recStack[course] = true;
        return true;
    }

    private void testJudge() {
        int[][] trusts = {{1, 2}, {2, 3}};
        int judge = findJudge(3, trusts);
        System.out.println("Judge: " + judge);
    }

    public int findMaxLength(int[] nums) {
        int maxLen = 0;
        Map<Integer, Integer> SUM_END_INDEX_MAP = new HashMap<>();
        SUM_END_INDEX_MAP.put(0, -1);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += (nums[i] == 0) ? -1 : 1;
            if (SUM_END_INDEX_MAP.containsKey(sum)) {
                maxLen = Math.max(maxLen, i - SUM_END_INDEX_MAP.get(sum));
            } else {
                SUM_END_INDEX_MAP.put(sum, i);
            }
        }
        return maxLen;
    }

    private void testLines() {
        int[] A = {1, 3, 7, 1, 7, 5};
        int[] B = {1, 9, 2, 5, 1};
        int max = maxUncrossedLines(A, B);
    }

    public int findJudge(int N, int[][] trust) {
        if (trust.length == 0)
            return N;
        int[] internal = new int[N + 1];
        int[] outgoing = new int[N + 1];
        for (int i = 0; i < trust.length; i++) {
            internal[trust[i][0]]++;
            outgoing[trust[i][1]]++;
        }
        for (int i = 0; i <= N; i++) {
            if (internal[i] == 0 && outgoing[i] == N - 1)
                return i;
        }
        return -1;
            /*Set<Integer> TRUSTS = new HashSet<>();
            Map<Integer, int[]> TRUSTED = new HashMap<>();
            for(int[] relation : trust) {
                TRUSTS.add(relation[0]);
                TRUSTED.put(relation[0], relation);
            }
            return IntStream.rangeClosed(1, N).reduce((a, b) -> potentialJudge(a, b, TRUSTS, TRUSTED)).orElse(-1);*/
    }

    private OptionalInt potentialJudge(int a, int b, Set<Integer> TRUSTS, Map<Integer, int[]> map) {
        int[] relation = map.get(a);
        if (relation != null) {
        }
        return OptionalInt.empty();
    }

    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] DP = new int[A.length + 1][B.length + 1];
        /**
         * DP[i+1][j+1] = IF A[i] == B[j] 1+DP[i-1][j-1]
         *               ELSE MAX(DP[i-1][j], DP[i][j-1])
         */
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                if (A[i] == B[j]) {
                    DP[i + 1][j + 1] = 1 + DP[i][j];
                } else {
                    DP[i + 1][j + 1] = Math.max(DP[i][j + 1], DP[i + 1][j]);
                }
            }
        }
        return DP[A.length][B.length];
    }

    private int countLines(int[] source, int[] dest) {
        //int s = 0;
        int d = -1;
        int count = 0;
        for (int s = 0; s < source.length; s++) {
            int match = matchingDest(source[s], d, dest);
            if (match >= 0 && match < dest.length) {
                count += 1;
                d = match;
            }
        }

        return count;
    }

    private int matchingDest(int val, int startIndex, int[] dest) {
        int index = -1;
        for (index = startIndex + 1; index < dest.length; index++) {
            if (dest[index] == val) {
                break;
            }
        }
        return index;
    }

    private void testReverseStr() {
        String str = "Let's take LeetCode contest";
        String rev = reverseWords(str);
        System.out.println(rev);
    }

    private void testSubSet() {
        List<List<String>> favComp = new ArrayList<>();
        favComp.add(Lists.newArrayList("leetcode"));
        favComp.add(Lists.newArrayList("google"));
        favComp.add(Lists.newArrayList("facebook"));
        favComp.add(Lists.newArrayList("amazon"));
        List<Integer> res = peopleIndexes(favComp);
        System.out.printf("%s\n", res.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }

    public List<Integer> splitIntoFibonacci(String S) {
        int len = S.length();
        List<Integer> fib = new ArrayList<>();
        for (int i = 0; i < Math.min(10, len); i++) {
            if (S.charAt(i + 1) == '0' && i > 0) {
                break;
            }
            long first = Long.valueOf(S.substring(0, i + 1));
            if (first >= Integer.MAX_VALUE) {
                break;
            }
            search:
            for (int j = i + 1; j < Math.min(i + 10, len); j++) {
                if (S.charAt(i + 1) == '0' && j > i + 1) {
                    break;
                }
                long second = Long.valueOf(S.substring(i + 1, j + 1));
                if (second >= Integer.MAX_VALUE) {
                    break;
                }
                fib = new ArrayList<>();
                fib.add(Long.valueOf(first).intValue());
                fib.add(Long.valueOf(second).intValue());
                int k = j + 1;
                while (k < len) {
                    long next = fib.get(fib.size() - 2) + fib.get(fib.size() - 1);
                    String nextStr = String.valueOf(next);
                    if (next < Integer.MAX_VALUE && S.substring(k).startsWith(nextStr)) {
                        k += nextStr.length();
                        fib.add(Long.valueOf(next).intValue());
                    } else {
                        continue search;
                    }
                }
                if (fib.size() >= 3) {
                    return fib;
                }
            }
        }
        return fib;
    }

    public String reverseWords(String str) {
        return Stream.<String>of(str.split("\\s")).map(e -> reverse(e)).collect(Collectors.joining(" "));
    }

    private String reverse(String str) {
        char[] array = str.toCharArray();
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            char ch = array[start];
            array[start++] = array[end];
            array[end--] = ch;
        }
        return String.valueOf(array);
    }

    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        List<Integer> res = new ArrayList<Integer>();
        List<Set<String>> fav = new ArrayList<Set<String>>();
        for (int i = 0; i < favoriteCompanies.size(); i++) {
            fav.add(new HashSet<String>(favoriteCompanies.get(i)));
        }
        for (int i = 0; i < fav.size(); i++) {
            boolean isSubset = false;
            Set<String> current = fav.get(i);
            for (int j = 0; j < fav.size(); j++) {
                if (i == j) {
                    continue;
                }
                Set<String> probe = fav.get(j);
                if (isSubset(current, probe)) {
                    isSubset = true;
                    break;
                }

            }
            if (!isSubset) {
                res.add(i);
            }

        }
        return res;
    }

    private boolean isSubset(Set<String> set1, Set<String> set2) {
        if (set1.size() > set2.size()) {
            return false;
        }
        //Set<String> set1 = new HashSet<String>(list1);
        Set<String> set3 = new HashSet<String>(set2);
        set3.retainAll(set1);
        return set3.size() == set1.size();

    }

    private void testCreateBstFromPreOrder() {
        int[] preorder = {8, 5, 1, 7, 10, 12};
        TreeNode root = bstFromPreorder(preorder);
    }

    private void testMinSize() {
        int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int res = shipWithinDays(weights, 5);
    }

    private void testCousin() {
        TreeNode root = createTree1();
        System.out.println(isCousins(root, 2, 3));
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int startingPos = 0;
        int totalCost = 0;
        int currentCost = 0;
        for (int i = 0; i < gas.length; i++) {
            currentCost = (gas[i] - cost[i]);
            totalCost += currentCost;
            if (currentCost < 0) {
                currentCost = 0;
                startingPos = i + 1;
            }
        }
        return startingPos;
    }

    public boolean canPartition(int[] nums) {
        int sum = 0;

        for (int num : nums) {
            sum += num;
        }

        if ((sum & 1) == 1) {
            return false;
        }
        sum /= 2;

        int n = nums.length;
        boolean[] dp = new boolean[sum + 1];
        Arrays.fill(dp, false);
        dp[0] = true;

        for (int num : nums) {
            for (int i = sum; i > 0; i--) {
                if (i >= num) {
                    dp[i] = dp[i] || dp[i - num];
                }
            }
        }

        return dp[sum];
    }

    private boolean canBePartitioned(int[] nums) {


        int sum = 0;

        for (
                int num : nums) {
            sum += num;
        }

        if ((sum & 1) == 1) {
            return false;
        }

        sum /= 2;

        int n = nums.length;
        boolean[][] dp = new boolean[n + 1][sum + 1];
        for (
                int i = 0;
                i < dp.length; i++) {
            Arrays.fill(dp[i], false);
        }

        dp[0][0] = true;

        for (
                int i = 1;
                i < n + 1; i++) {
            dp[i][0] = true;
        }
        for (
                int j = 1;
                j < sum + 1; j++) {
            dp[0][j] = false;
        }

        for (
                int i = 1;
                i < n + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i - 1]) {
                    dp[i][j] = (dp[i][j] || dp[i - 1][j - nums[i - 1]]);
                }
            }
        }

        return dp[n][sum];
    }

    public int shipWithinDays(int[] weights, int D) {
        int hi = 0;
        int lo = Integer.MIN_VALUE;
        for (int weight : weights) {
            hi += weight;
            lo = Math.max(lo, weight);
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canBeShippedInDays(weights, mid, D)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private boolean canBeShippedInDays(int[] weights, int maxCapacity, int days) {
        int required = 1;
        int currentCapacity = 0;
        for (int currWeight : weights) {
            if ((currentCapacity + currWeight) <= maxCapacity) {
                currentCapacity += currWeight;
            } else {
                currentCapacity = currWeight;
                required += 1;
            }
        }
        boolean cudBeShipped = false;
        if (required <= days) {
            cudBeShipped = true;
        }
        return cudBeShipped;
    }

    private void testTraversal() {
        TreeNode root = createTree();
        List<List<Integer>> res = levelOrder(root);
        for (List<Integer> level : res) {
            level.stream().map(String::valueOf).collect(Collectors.joining(","));
        }
    }


    //int  i = 0;
    public TreeNode bstFromPreorder(int[] preorder) {
        int[] arr = {1};
        return bstFromPreorder(preorder, Integer.MAX_VALUE, arr);
    }

    public TreeNode bstFromPreorder(int[] preorder, int bound, int[] arr) {
        if (arr[0] == preorder.length || preorder[arr[0]] > bound) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[arr[0]++]);
        root.left = bstFromPreorder(preorder, root.val, arr);
        root.right = bstFromPreorder(preorder, bound, arr);
        return root;
    }

    public boolean isCousins(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }
        List<TreeNode> parent = new ArrayList();
        List<TreeNode> child = new ArrayList();
        parent.add(root);
        while (!parent.isEmpty()) {
            //Set<Integer> label = new ArrayList();
            boolean gotAny = false;
            for (TreeNode node : parent) {
                if (node.val == x || node.val == y) {
                    if (gotAny) {
                        return true;
                    } else {
                        gotAny = true;
                    }
                }
                if (node.left != null) child.add(node.left);
                if (node.right != null) child.add(node.right);
            }
            if (gotAny) {
                break;
            }
            parent = child;
            child = new ArrayList<>();
        }
        return false;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList();
        if (root == null) {
            return res;
        }
        List<TreeNode> parent = new ArrayList();
        List<TreeNode> child = new ArrayList();
        parent.add(root);
        while (!parent.isEmpty()) {
            List<Integer> label = new ArrayList();
            for (TreeNode node : parent) {
                label.add(node.val);
                if (node.left != null) child.add(node.left);
                if (node.right != null) child.add(node.right);
            }
            res.add(label);
            parent = child;
            child = new ArrayList<>();
        }
        return res;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n * k == 0) return new int[0];
        if (k == 1) return nums;

        int[] left = new int[n];
        left[0] = nums[0];
        int[] right = new int[n];
        right[n - 1] = nums[n - 1];

        for (int i = 1; i < n; i++) {
            if (i % k == 0) {//Block Start
                left[i] = nums[i];
            } else {
                left[i] = Integer.max(left[i - 1], nums[i]);
            }

            int j = n - i - 1;

            if ((j + 1) % k == 0) {
                right[j] = nums[j];
            } else {
                right[j] = Integer.max(right[j + 1], nums[j]);
            }
        }
        int[] res = new int[n - k + 1];
        for (int i = 0; i < n - k + 1; i++) {
            res[i] = Math.max(right[i], left[i + k - 1]);   //max(right[i], left[j]);
        }

        return res;
    }


    public int maximalRectangle(char[][] matrix) {

        if (matrix.length == 0) return 0;
        int maxarea = 0;
        int[] dp = new int[matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                // update the state of this row's histogram using the last row's histogram
                // by keeping track of the number of consecutive ones

                dp[j] = matrix[i][j] == '1' ? dp[j] + 1 : 0;
            }
            // update maxarea with the maximum area from this row's histogram
            maxarea = Math.max(maxarea, leetcode84(dp));
        }
        return maxarea;
    }

    public int leetcode84(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
                maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
            stack.push(i);
        }
        while (stack.peek() != -1)
            maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
        return maxarea;
    }

    public int getMostWork(int[] cabinets, int empCount) {
        int hi = 0;
        int lo = Integer.MIN_VALUE;
        for (int current_cab_docs : cabinets) {
            hi += current_cab_docs;
            lo = Integer.max(lo, current_cab_docs);
        }

        while (lo < hi) {
            int x = lo + (hi - lo) / 2;
            if (canAccomodateDocuments(cabinets, x, empCount)) {
                hi = x;
            } else {
                lo = x + 1;
            }
        }
        return lo;
    }

    private boolean canAccomodateDocuments(int[] cabinets, int docCount, int empCount) {
        int required = 1;
        int currentLoad = 0;
        for (int currentCabinet : cabinets) {
            if (currentLoad + currentCabinet < docCount) {
                currentLoad += currentCabinet;
            } else {
                required += 1;
                currentLoad = 0;
            }
        }
        boolean cudAccomodate = false;
        if (required <= empCount) {
            cudAccomodate = true;
        }
        return cudAccomodate;
    }

    private TreeNode createTree1() {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        left.right = new TreeNode(4);
        root.left = left;
        root.right = new TreeNode(3);
        return root;
    }

    private TreeNode createTree() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        TreeNode right = new TreeNode(20);
        right.left = new TreeNode(15);
        right.right = new TreeNode(7);
        root.right = right;
        return root;
    }

    private void connectLeft(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            root.left.next = root.right;
        }
        if (root.next != null && root.right != null) {
            root.right.next = root.next.left;
        }
        connectLeft(root.left);
        connectLeft(root.right);
    }

    public TreeNode bstFromPreorder1(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        return bstFromPreorder(preorder, 0, preorder.length - 1);
    }

    public TreeNode bstFromPreorder(int[] preorder, int index, int end) {
        if (index > end) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[index]);
        int trans = transition(preorder, index);
        root.left = bstFromPreorder(preorder, index + 1, trans - 1);
        root.right = bstFromPreorder(preorder, trans, end);
        return root;
    }

    private int transition(int[] preorder, int index) {
        int trans = index;
        while (trans < preorder.length && preorder[trans] <= preorder[index]) {
            trans += 1;
        }
        return trans;
    }

    /**
     * Definition for a binary tree node.
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode next;

        TreeNode(int x) {
            val = x;
        }
    }
}
