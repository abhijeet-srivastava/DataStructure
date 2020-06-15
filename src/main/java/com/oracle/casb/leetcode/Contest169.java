package com.oracle.casb.leetcode;

import com.oracle.casb.common.ListNode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summingInt;

public class Contest169 {

    public static void main(String[] args) {
        Contest169 app = new Contest169();
        //app.testJump3();
        //app.testJump();
        //app.testIlluminatd();
        //app.testString();
        //app.toggle();
        //app.testMinInsertions();
        //app.testMinLexico();
        //app.testParanScore();
        //app.testKreverse();
        //app.testDuplicates();
        //app.testDeleteDuplicates();
        //app.testMergeList();
        app.testListPalindrome();
    }

    private void testListPalindrome() {
        ListNode head = createList1();
        printList(head);
        ListNode rev = reverseList(head);
        printList(rev);
        boolean res = isPalindrome(head);
        System.out.println(res);
    }

    private ListNode createPdList() {
        ListNode head = new ListNode(1);
        head.setNext(new ListNode(2));
        return head;
    }

    public boolean isPalindrome(ListNode head) {
        ListNode reverse = reverseList(head);
        return isEqual(head, reverse);
    }

    private boolean isEqual(ListNode head, ListNode reverse) {
        if (head  == null && reverse  == null) {
            return true;
        } else if (head == null ||  reverse  == null) {
            return false;
        }
        return (head.val == reverse.val) && isEqual(head.next, reverse.next);
    }

    private ListNode  reverseList(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode[] reverse = new ListNode[1];
        ListNode node = reverse(head, reverse);
        node.setNext(null);
        return reverse[0];
    }

    private ListNode reverse(ListNode head, ListNode[] reverse) {
        if(head.next == null) {
            ListNode node = new ListNode(head.val);
            reverse[0] = node;
            return node;
        }
        ListNode reversedList = reverse(head.next, reverse);
        reversedList.next = new ListNode(head.val);
        return reversedList.next;
    }
    public ListNode reverseList1(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode p = reverseList1(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    public ListNode reverseListitr(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    private void testMergeList() {
        ListNode[] lists = {createList1(), createList2(), createList3()};
        ListNode head = mergeKLists(lists);
        printList(head);
    }
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = null;
        ListNode current = null;
        boolean end = false;
        while(!end) {
            //end = true;
            //int minVal = Integer.MAX_VALUE;
            ListNode minNode = new ListNode(Integer.MAX_VALUE);
            int minIndex = -1;
            for (int i = 0; i < lists.length; i++) {
                ListNode listHead = (lists[i] == null) ? new ListNode(Integer.MAX_VALUE) : lists[i];
                if (listHead.val < minNode.val) {
                    minIndex = i;
                    minNode = listHead;
                }
            }
            if(minIndex == -1) {
                end = true;
            } else if(head == null){
                head = lists[minIndex];
                current = head;
                lists[minIndex] = lists[minIndex].next;
            } else {
                current.next = lists[minIndex];
                current = current.next;
                lists[minIndex] = lists[minIndex].next;
            }
        }
        current.next = null;
        return head;
    }

    private void testDeleteDuplicates() {
        ListNode head = createListdd();
        printList(head);
        head = deleteDuplicates2(head);
        printList(head);
    }

    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null) return null;

        if (head.next != null && head.val == head.next.val) {
            while (head.next != null && head.val == head.next.val) {
                head = head.next;
            }
            return deleteDuplicates(head.next);
        } else {
            head.next = deleteDuplicates(head.next);
        }
        return head;
    }
    public ListNode deleteDuplicates1(ListNode head) {
        if(head==null) return null;
        ListNode FakeHead=new ListNode(1);
        FakeHead.next=head;
        ListNode pre=FakeHead;
        ListNode cur=head;
        while(cur!=null){
            while(cur.next!=null&&cur.val==cur.next.val){
                cur=cur.next;
            }
            if(pre.next==cur){
                pre=pre.next;
            }  else{
                pre.next=cur.next;
            }
            cur=cur.next;
        }
        return FakeHead.next;
    }
    public int numComponents(ListNode head, int[] G) {
        Set<Integer> set = new HashSet<>();
        for (int i  : G) {
            set.add(i);
        }
        int cc = 0;
        ListNode tmp = head;
        while (tmp != null) {
            if (set.contains(tmp.val)) {
                while (tmp != null && set.contains(tmp.val)) {
                    tmp = tmp.next;
                }
                cc += 1;
            } else {
                tmp = tmp.next;
            }
        }
        return cc;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode prev = head;
        ListNode current = prev.next;
        while(current != null) {
            while(current != null && prev.val == current.val) {
                current = current.next;
            }
            prev.next = current;
            prev = current;
            if (prev != null) {
                current = prev.next;
            }
        }
        return head;
    }
    private void testDuplicates() {
        ZonedDateTime zdt = ZonedDateTime.now().toLocalDate().atStartOfDay(ZoneId.systemDefault());
        System.out.println(zdt);
        int[] arr = {4,3,2,7,8,2,3,1};
        List<Integer> list = findDuplicates(arr);
        System.out.println(list.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

    public List<Integer> findDuplicates(int[] nums) {
        boolean[] count = new boolean[nums.length+1];
        Set<Integer> set = new HashSet<>();
        Arrays.fill(count, Boolean.TRUE);
        for (int i : nums) {
            set.add(i);
            count[i] = !count[i];
        }
        List<Integer> list = new ArrayList<>();

        for (int i = 1; i < count.length; i++) {
            if(count[i] && set.contains(i)) {
                list.add(i);
            }
        }
        return list;
    }
    private int countInRange(int[] nums, int num, int lo, int hi) {
        int count = 0;
        for (int i = lo; i <= hi; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }

    private int majorityElementRec(int[] nums, int lo, int hi) {
        // base case; the only element in an array of size 1 is the majority
        // element.
        if (lo == hi) {
            return nums[lo];
        }

        // recurse on left and right halves of this slice.
        int mid = (hi-lo)/2 + lo;
        int left = majorityElementRec(nums, lo, mid);
        int right = majorityElementRec(nums, mid+1, hi);

        // if the two halves agree on the majority element, return it.
        if (left == right) {
            return left;
        }

        // otherwise, count each element and return the "winner".
        int leftCount = countInRange(nums, left, lo, hi);
        int rightCount = countInRange(nums, right, lo, hi);

        return leftCount > rightCount ? left : right;
    }

    public int majorityElement(int[] nums) {
        return majorityElementRec(nums, 0, nums.length-1);
    }

    private void testKreverse() {
        ListNode head = createList(19);
        printList(head);
        ListNode reverseHead = reverseKGroup(head, 4);
        printList(reverseHead);
    }

    private void printList(ListNode head) {
        while (head != null) {
            System.out.printf("%d", head.val);
            if(head.next != null) {
                System.out.printf("%c", ',');
            }
            head = head.next;
        }
        System.out.printf("%c", '\n');
    }


    private ListNode createList1() {
        ListNode head = new ListNode(1);
        head.setNext(new ListNode(4));
        head.getNext().setNext(new ListNode(5));
        head.getNext().getNext().setNext(new ListNode(4));
        head.getNext().getNext().getNext().setNext(new ListNode(1));

        return head;
    }
    private ListNode createList2() {
        ListNode head = new ListNode(1);
        head.setNext(new ListNode(3));
        head.getNext().setNext(new ListNode(4));

        return head;
    }
    private ListNode createList3() {
        ListNode head = new ListNode(2);
        head.setNext(new ListNode(6));

        return head;
    }
    private ListNode createListdd() {
        ListNode head = new ListNode(1);

        head.setNext(new ListNode(1));
        head.getNext().setNext(new ListNode(1));
        head.getNext().getNext().setNext(new ListNode(2));
        head.getNext().getNext().getNext().setNext(new ListNode(2));
        head.getNext().getNext().getNext().getNext().setNext(new ListNode(3));

        /*head.setNext(new ListNode(2));
        head.getNext().setNext(new ListNode(2));
        head.getNext().getNext().setNext(new ListNode(2));*/

        /*head.setNext(new ListNode(1));
        head.getNext().setNext(new ListNode(1));
        head.getNext().getNext().setNext(new ListNode(2));
        head.getNext().getNext().getNext().setNext(new ListNode(3));*/

        /*head.setNext(new ListNode(2));
        head.getNext().setNext(new ListNode(3));
        head.getNext().getNext().setNext(new ListNode(3));
        head.getNext().getNext().getNext().setNext(new ListNode(4));
        head.getNext().getNext().getNext().getNext().setNext(new ListNode(4));
        head.getNext().getNext().getNext().getNext().getNext().setNext(new ListNode(5));*/

        return head;
    }
    private ListNode createList(int count) {
        ListNode head = new ListNode(1);
        ListNode tmp = head;
        for(int i = 2; i <= count; i++) {
            ListNode node = new ListNode(i);
            tmp.next = node;
            tmp = tmp.next;
        }
        return head;
    }

    public ListNode reverseKGroup1(ListNode head, int k) {
        if(head == null || k == 1) {
            return head;
        }
        ListNode tmp = head;
        int i = 1;
        while(i < k &&  tmp != null) {
            tmp = tmp.next;
            i += 1;
        }
        if(tmp == null || i < k) {
            return head;
        }
        ListNode nxt = reverseKGroup(tmp.next, k);
        ListNode t1 = head;
        ListNode t2 = head.next;
        while (i > 1 && t2 != null) {
            ListNode t3 = t2.next;
            t2.next = t1;
            t1 = t2;
            t2 = t3;
            i-= 1;
        }
        head.next = nxt;
        return tmp;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || k == 1) {
            return head;
        }
        ListNode tmp = head;
        int i = 1;
        while(i < k &&  tmp != null) {
            tmp = tmp.next;
            i += 1;
        }
        ListNode nxt = null;
        if(tmp != null) {
            nxt = reverseKGroup(tmp.next, k);
        }
        ListNode t1 = head;
        ListNode t2 = head.next;
        while (i > 1 && t2 != null) {
            ListNode t3 = t2.next;
            t2.next = t1;
            t1 = t2;
            t2 = t3;
             i-= 1;
        }
        //t1.next = null;
        head.next = nxt;
        return t1;
    }
    private void testParanScore() {
        int score = scoreOfParentheses("(()(()))");
        System.out.println(score);
    }

    public int scoreOfParentheses(String S) {
        int score = 0;
        if(S == null || S.isEmpty()) {
            return 0;
        } else if(S.equals("()")) {
            return 1;
        }
        int i = 0;
        char[] arr = S.toCharArray();
        while(i < arr.length){
            int j = matchingIndex(arr, i);
            int leftScore = (j - i)  == 1 ? 1 :  scoreOfParentheses(S.substring(i+1, j));
            int rightScore = (S.length() - j)  == 1 ? 1 :  scoreOfParentheses(S.substring(j+1));
            score = 2 * leftScore + rightScore;
            i = j+1;
        }
        return score;
    }
    private int matchingIndex(char[] arr, int index) {
        int count = 1;
        int ind = index;
        for (int i = index + 1; i < arr.length; i++) {
            if(arr[i] == '(') {
                count += 1;
            } else if (arr[i] == ')') {
                count -= 1;
            }
            if(count == 0) {
                ind = i;
                break;
            }
        }
        return ind;
    }

    private void testMinLexico() {
        String str = lastSubstring("zrziy");
        System.out.println(str);
    }

    public String lastSubstring(String s) {
        int index = 0;
        char[] arr = s.toCharArray();
        char max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if(isSol(arr, index, i))  {
                max = arr[i];
                index = i;
            }
        }
        return s.substring(index);
    }

    private boolean isSol(char[] arr, int min, int current) {
        boolean isSol = false;
        if (arr[current] < arr[min]) {
            return isSol;
        } else if (arr[current] > arr[min]) {
            return true;
        }
        while(current < arr.length && arr[min] == arr[current]) {
            current += 1;
            min += 1;
        }
        if (current == arr.length) {
            return false;
        } else {
            return arr[min] < arr[current];
        }
    }

    public int minInsertions(String s) {
        Map<String, Integer> DP = new HashMap<>();
        return minInsertions(s.toCharArray(), 0, s.length() - 1, DP);
    }

    private int minInsertions(char[] chatArr, int i, int j, Map<String, Integer> DP) {
        if (i >= j) {
            return 0;
        }
        String key = i + "_" + j;
        if (DP.containsKey(key)) {
            return DP.get(key);
        }
        int minDistance = (chatArr[i] == chatArr[j])
                ? minInsertions(chatArr, i + 1, j - 1, DP)
                : 1 + Math.min(minInsertions(chatArr, i + 1, j, DP), minInsertions(chatArr, i, j - 1, DP));
        DP.put(key, minDistance);
        return minDistance;
    }

    private void testMinInsertions() {
        //String str = "leetcode";
        String str = "dyyuyabzkqaybcspq";
        getDiff("dyyuyabzkq", "aybcspq");
        int l = str.length();
        /*int i = l/2;
        int j = l/2;
        if (l%2 == 0) {
            j += 1;
        }*/
        int maxDiff = str.length();
        for (int i = 0; i < l; i++) {
            String str1 = str.substring(0, i);
            String str2 = str.substring(i);
            int diff = getDiff(str1, str2);
            System.out.printf("%s - %s = %d\n", str1, str2, diff);
            maxDiff = Integer.min(maxDiff, diff);

            str1 = str.substring(0, i);
            str2 = str.substring(i + 1);
            diff = getDiff(str1, str2);
            System.out.printf("%s - %s = %d\n", str1, str2, diff);
            maxDiff = Integer.min(maxDiff, diff);
        }
        System.out.println("Max Diff " + maxDiff);
    }

    private int getDiff(String str1, String str2) {
        int totalDiff = 0;
        Map<Character, Integer> map1 = getFrequencies(str1);
        Map<Character, Integer> map2 = getFrequencies(str2);
        for (Map.Entry<Character, Integer> e : map1.entrySet()) {
            if (map2.containsKey(e.getKey())) {
                totalDiff += Math.abs(e.getValue() - map2.get(e.getKey()));
            } else {
                totalDiff += e.getValue();
            }
        }
        for (Map.Entry<Character, Integer> e : map2.entrySet()) {
            if (!map1.containsKey(e.getKey())) {
                totalDiff += e.getValue();
            }
        }
        return totalDiff;
    }

    private Map<Character, Integer> getFrequencies(String word) {
        return word.chars()
                .mapToObj(c -> Character.toString((char) c))
                .map(e -> e.charAt(0))
                .map(Character::charValue)
                .collect(Collectors.groupingBy(Function.identity(), summingInt(e -> 1)));
    }

    enum Index {
        GOOD, BAD, UNKNOWN
    }

    public boolean canJump1(int[] nums) {
        Integer[] dp = new Integer[nums.length];
        Arrays.fill(dp, 2);
        dp[dp.length - 1] = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            int furthestJump = Math.min(i + nums[i], nums.length - 1);
            for (int j = i + 1; j <= furthestJump; j++) {
                if (dp[j] == 0) {
                    dp[i] = 0;
                    break;
                }
            }
        }

        return dp[0] == 0;
    }

    public boolean canJump(int[] nums) {
        Index[] memo = new Index[nums.length];
        Arrays.fill(memo, Index.UNKNOWN);
        memo[memo.length - 1] = Index.GOOD;
        for (int i = nums.length - 2; i >= 0; i--) {
            int furthestJump = Math.min(i + nums[i], nums.length - 1);
            for (int j = i + 1; j <= furthestJump; j++) {
                if (memo[j] == Index.GOOD) {
                    memo[i] = Index.GOOD;
                    break;
                }
            }
        }

        return memo[0] == Index.GOOD;
    }

    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(id);
        int count = 0;
        Set<Integer> visited = new HashSet<>();
        visited.add(id);
        while (!q.isEmpty()) {
            if (count == level) {
                break;
            }
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int p = q.poll();
                for (int f : friends[p]) {
                    if (!visited.contains(f)) {
                        q.add(f);
                        visited.add(f);
                    }
                }
            }
            count++;
        }
        Map<String, Integer> map = new HashMap<>();
        List<String> res = new ArrayList<>();
        while (!q.isEmpty()) {
            int p = q.poll();
            List<String> vs = watchedVideos.get(p);
            for (String v : vs) {
                map.put(v, map.getOrDefault(v, 0) + 1);
            }
        }
        for (String key : map.keySet()) {
            res.add(key);
        }
        Collections.sort(res, (a, b) -> (map.get(a) == map.get(b) ? a.compareTo(b) : map.get(a) - map.get(b)));
        return res;
    }

    public int[] xorQueries(int[] A, int[][] queries) {
        int[] res = new int[queries.length], q;
        for (int i = 1; i < A.length; ++i)
            A[i] ^= A[i - 1];
        for (int i = 0; i < queries.length; ++i) {
            q = queries[i];
            res[i] = q[0] > 0 ? A[q[0] - 1] ^ A[q[1]] : A[q[1]];
        }
        return res;
    }

    private void toggle() {
        int i = 0;
        int x = 5;
        int y = 15;
        for (int n = x; i < 25; n ^= (x ^ y), i++) {
            System.out.printf("%d \n", n);
        }
    }

    private void testString() {
        System.out.printf("%s%c", freqAlphabets("12345678910#11#12#13#14#15#16#17#18#19#20#21#22#23#24#25#26#"), '\n');
    }

    public String freqAlphabets(String s) {
        Map<Integer, Character> map = new HashMap<>();
        int i = 1;
        char ch = 'a';
        while (i <= 26) {
            map.put(Integer.valueOf(i++), Character.valueOf(ch++));
        }
        return freqAlphabets(s, "", map);
    }

    private String freqAlphabets(String str, String suffix, Map<Integer, Character> map) {
        if (str.isEmpty()) {
            return suffix;
        }
        if (str.charAt(str.length() - 1) == '#') {
            Character ch = map.get(Integer.valueOf(str.substring(str.length() - 3, str.length() - 1)));
            suffix = ch + suffix;
            return freqAlphabets(str.substring(0, str.length() - 3), suffix, map);
        } else {
            Character ch = map.get(Integer.valueOf(str.substring(str.length() - 1, str.length())));
            suffix = ch + suffix;
            return freqAlphabets(str.substring(0, str.length() - 1), suffix, map);
        }
    }

    private void testIlluminatd() {
        int[][] lamps = {{0, 0}, {0, 4}};
        int[][] queries = {{0, 4}, {0, 1}, {1, 4}};
        System.out.printf("%s\n", Arrays.stream(gridIllumination(5, lamps, queries))
                .mapToObj(Integer::valueOf)
                .map(String::valueOf).collect(Collectors.joining(",")));
    }

    private void testJump() {
        int[] arr = {2, 3, 1, 1, 4};
        System.out.println(jumpCopy(arr));
    }

    public int[] gridIllumination(int N, int[][] lamps, int[][] queries) {
        int[] res = new int[queries.length];
        int[][] nlamps = new int[lamps.length][3];
        int i = 0;
        for (int[] lamp : lamps) {
            nlamps[i][0] = lamp[0];
            nlamps[i][1] = lamp[1];
            nlamps[i++][2] = 1;
        }
        i = 0;
        for (int[] query : queries) {
            res[i++] = isIlluminated(query, nlamps, N);
        }
        return res;
    }

    private int isIlluminated(int[] query, int[][] lamps, int size) {
        int illuminating = 0;
        for (int[] lamp : lamps) {
            if (lamp[2] == 1) {
                if (isSameRow(lamp, query)
                        || isSameColumn(lamp, query)
                        || isSameDiagonal(lamp, query, size)) {
                    illuminating = 1;
                    if (isOnBundry(lamp, query, size)) {
                        lamp[2] = 0;
                    }
                }
            }
        }
        return illuminating;
    }

    private boolean isOnBundry(int[] lamp, int[] query, int size) {
        int[][] boundries = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] boundry : boundries) {
            int row = query[0] + boundry[0];
            int column = query[1] + boundry[1];
            if (row >= 0 && row < size && column >= 0 && column < size && lamp[0] == row && lamp[1] == column) {
                return true;
            }
        }
        return false;
    }

    private boolean isSameDiagonal(int[] lamp, int[] query, int size) {
        int rowDiff = Math.abs(lamp[0] - query[0]);
        int colDiff = Math.abs(lamp[1] - query[1]);
        return rowDiff == colDiff;
    }

    private boolean isSameRow(int[] lamp, int[] query) {
        return lamp[0] == query[0];
    }

    private boolean isSameColumn(int[] lamp, int[] query) {
        return lamp[1] == query[1];
    }

    public int jump(int[] nums) {
        int[] jumps = new int[nums.length];
        Arrays.fill(jumps, Integer.MAX_VALUE);
        jumps[0] = 0;
        for (int i = 0; i < jumps.length; i++) {
            for (int j = i + 1; j < jumps.length && j <= i + nums[i]; j++) {
                jumps[j] = Integer.min(jumps[j], jumps[i] + 1);
            }
        }
        return jumps[nums.length - 1];
    }

    public int jumpCopy(int[] A) {
        int jumps = 0, curEnd = 0, curFarthest = 0;
        for (int i = 0; i < A.length - 1; i++) {
            curFarthest = Math.max(curFarthest, i + A[i]);
            if (i == curEnd) {
                jumps++;
                curEnd = curFarthest;

                if (curEnd >= A.length - 1) {
                    break;
                }
            }
        }
        return jumps;
    }

    private void testJump3() {
        int[] arr = {4, 2, 3, 0, 3, 1, 2};
        System.out.println(canReach(arr, 5));
    }

    public boolean canReach(int[] arr, int start) {
        if (start < 0 || start >= arr.length) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        return canReach(arr, start, visited);
    }

    private boolean canReach(int[] arr, int start, Set<Integer> visited) {
        if (start < 0 || start >= arr.length) {
            return false;
        } else if (arr[start] == 0) {
            return true;
        }
        int left = start - arr[start];
        int right = start + arr[start];
        boolean canReach = false;
        if (!visited.contains(left)) {
            visited.add(left);
            canReach = canReach(arr, left, visited);
        }
        if (!canReach && !visited.contains(right)) {
            visited.add(right);
            canReach = canReach(arr, right, visited);
        }
        return canReach;
    }

    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
               List<Interval> merged = schedule.stream()
                       .flatMap(e -> e.stream()).sorted((a,b) -> (a.start != b.start) ? (a.start - b.start) : (a.end - b.end))
               .collect(Collectors.toList());
               //Stack<Interval> stack = new Stack<>();
               List<Interval> result = new ArrayList<>();
               Interval in = merged.get(0);
               for (Interval interval : merged) {
                   if(in.end < interval.start)  {
                       result.add(new Interval(in.end, interval.start));
                       in = interval;
                   } else if (in.end < interval.end) {
                       in = interval;
                   }
               }
               return result;
    }
    public class Interval {
        int start;
        int end;
        Interval() {
            start = 0;
            end = 0;
        }
        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }
}
