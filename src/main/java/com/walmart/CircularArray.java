package com.walmart;

import java.util.*;
import java.util.stream.Collectors;

public class CircularArray {

    private static final Integer UNVISITED = 0;
    private static final Integer IN_PROGRESS = 1;
    private static final Integer VISITED = 2;

    public static void main(String[] args) {
        CircularArray ca = new CircularArray();
        //ca.testCa();
        ca.testWordLadder();
        //ca.testNextPerm();
    }

    private void testNextPerm() {
        int[] arr = {15,15};
        String next = kthSmallestPath(arr, 155117520);
        System.out.println(next);
    }

    private void testWordLadder() {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> dictionary =  Arrays.asList("hot","dot","dog","lot","log","cog");
        List<List<String>> ladders = ladderLengthII(beginWord, endWord, dictionary);
        ladders.stream().forEach(
                ladder ->
                System.out.printf("Ladder %s\n", ladder.stream().collect(Collectors.joining(",", "[", "]")))
        );
    }

    private void testCa() {
        //int[] nums = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,-1};
        int[] nums = {-1,-2,-3,-4,-5};
        boolean isCircular = circularArrayLoop(nums);
        if(isCircular) {
            System.out.println("Is  Circular");
        } else {
            System.out.println("Not Circular");
        }
    }


    private boolean circularArrayLoop(int[] nums) {
        int[] COLOURS =  new int[nums.length];
        for(int i = 0; i <nums.length; i++) {
            if(COLOURS[i] == UNVISITED) {
                boolean isCircular = DFS(i, nums, COLOURS);
                if(isCircular) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean DFS(int start, int[] nums, int[] COLOURS) {
        if(COLOURS[start]  == VISITED) {
            return false;
        }
        COLOURS[start] = IN_PROGRESS;
        int next = advance(start, nums);
        if(next == start //1 Node cycle
                || ((nums[start] * nums[next]) < 0)) {//Opposite sign nodes
            return false;
        }
        if(COLOURS[next] == IN_PROGRESS) {
            COLOURS[start] = VISITED;
            return true;
        }
        if(DFS(next, nums, COLOURS)){
            return true;
        }
        COLOURS[start] = VISITED;
        return false;
    }

    public boolean circularArrayLoop1(int[] nums) {
        if(nums == null || nums.length < 2) {
            return false;
        }
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                continue;
            }
            int slow = i;
            int fast = advance(i, nums);
            while((nums[i] * nums[slow] > 0)
                    && (nums[i] * nums[fast] > 0)) {
                System.out.printf("Slow: nums[%d] = %d, Fast: nums[%d] = %d\n", slow, nums[slow], fast, nums[fast]);
                if(slow == fast) {
                    int ind = advance(slow, nums);
                    //System.out.printf("Index %d\n",ind);
                    if(slow == advance(slow, nums) ) {
                        break;
                    }
                    return true;
                }
                slow = advance(slow, nums);
                int skip = advance(fast, nums);
                System.out.printf("Fast skipping: nums[%d] = %d\n", skip, nums[skip]);
                if((nums[i] * nums[skip]) < 0) {
                    break;
                }
                fast = advance(skip, nums);
            }

            //int index = i;
            int index = advance(i, nums);
            while((nums[i]*nums[index]) > 0) {
                int tmp = advance(index, nums);
                nums[index] = 0;
                index = tmp;
            }
        }
        return false;
    }

    private int advance(int index, int[] nums) {
        int next = index + nums[index];
        next %= nums.length;
        if(next < 0) {
            next += nums.length;
        }
        return next;
    }


    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordset = new HashSet<>(wordList);
        if(beginWord == null || endWord == null
                || beginWord.length() != endWord.length()
                || !wordset.contains(endWord)) {
            return 0;
        }
        int ladder = 0;
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0){
                String current = queue.remove();
                visited.add(current);
                if(current.equals(endWord)) {
                    return ladder;
                }
                List<String> neighbour = nextNeighbours(current, wordset, visited);
                //visited.addAll(neighbour);
                queue.addAll(neighbour);
            }
            ladder += 1;
        }
        return 0;
    }

    public List<List<String>> ladderLengthII(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        List<List<String>> result = new ArrayList<>();
        List<String> path = new ArrayList<>();
        Map<String, Set<String>> childParent = new HashMap<>();
        if(beginWord == null || endWord == null
                || beginWord.length() != endWord.length()
                || !wordSet.contains(endWord)) {
            return result;
        }
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        childParent.put(beginWord, null);
        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0){
                String current = queue.remove();
                visited.add(current);
                if (current.equals(endWord)) {
                    dfs(endWord, beginWord, childParent, path, result);
                    return result;
                }
                List<String> neighbours = nextNeighbours(current, wordSet, visited);
                //visited.addAll(neighbours);
                for (String neighbour : neighbours) {
                    queue.offer(neighbour);
                    childParent.computeIfAbsent(neighbour, e -> new HashSet<>()).add(current);
                }
            }
        }
        return result;
    }

    private void dfs(String curWord, String endWord, Map<String, Set<String>> graph, List<String> path, List<List<String>> result) {
        path.add(curWord);
        if (curWord.equals(endWord)) result.add(new ArrayList<String>(path));
        else if (graph.containsKey(curWord)) {
            for (String nextWord : graph.get(curWord)) {
                dfs(nextWord, endWord, graph, path, result);
            }
        }
        path.remove(path.size()-1);
    }


    private List<String> nextNeighbours(String current, Set<String> dictionary, Set<String> visited) {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < current.length(); i++) {
            result.addAll(nextIndexNeighbours(current, i, dictionary, visited));
        }
        return result;
    }
    private List<String> nextIndexNeighbours(String current, int index, Set<String> dictionary, Set<String> visited) {
        List<String> result = new ArrayList<>();
        char[] array = current.toCharArray();
        for(char ch = 'a'; ch <= 'z'; ch++) {
            if(ch == array[index]) {
                continue;
            }
            array[index] = ch;
            String nextStr = String.valueOf(array);
            if(dictionary.contains(nextStr) && !visited.contains(nextStr)) {
                result.add(nextStr);
            }
        }
        return result;
    }

    public String kthSmallestPath(int[] A, int k) {
        int m = A[0], n = A[1];
        int len = m+n;
        int[][] dp = new int[len+1][len+1];
        dp[0][0] = 1;
        for(int i=1; i<=len; i++) {
            for(int j=0; j<=i; j++) {
                if(j == 0 || j == i)
                    dp[i][j] = 1;
                else {
                    dp[i][j] = dp[i-1][j] + dp[i-1][j-1];
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int down = m;
        for(int i=0; i<len; i++) {
            int remain = len - (i+1);
            int com = dp[remain][down];
            if(com >= k) {
                sb.append("H");
            } else {
                down -= 1;
                k -= com;
                sb.append("V");
            }
        }
        return sb.toString();
    }

    private boolean swapLast(char[] arr) {
        for(int i = arr.length-1; i > 0; i--) {
            if(arr[i] == 'V' && arr[i-1] == 'H') {
                arr[i-1] = 'V';
                arr[i] = 'H';
                Arrays.sort(arr, i+1, arr.length);
                return true;
            }
        }
        return false;
    }

    private int conVertToDecimal(char[] binary) {
        int value = 0;
        int pow = 1;
        for(int index = binary.length-1; index >= 0; index--) {
            if(binary[index] == '1') {
                value += pow;
            }
            pow <<= 1;
        }
        return value;
        // 3, 5, 6, 9,
    }
}
